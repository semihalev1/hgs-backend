import { useEffect, useState } from "react";
import { Form, Select, Button, Alert, Space, message } from "antd";
import axios from "axios";
import { type VehicleResponse } from "../services/vehicleService";
import {
  transactionService,
  type TransactionRequest,
} from "../services/transactionService";
import type { GateResponse } from "../services/gateService";
import {
  tariffService,
  type TariffQuoteResponse,
} from "../services/tariffService";

interface Props {
  vehicles: VehicleResponse[];
  gates: GateResponse[];
  gatesLoading: boolean;
  onRefresh: () => void;
}

interface ProblemDetailResponse {
  detail?: string;
  hatalar?: string[];
}

function extractErrorMessage(error: unknown): string {
  if (axios.isAxiosError<ProblemDetailResponse>(error)) {
    const data = error.response?.data;
    if (data) {
      if (Array.isArray(data.hatalar) && data.hatalar.length > 0) {
        return data.hatalar.join(" ");
      }
      if (typeof data.detail === "string" && data.detail.trim().length > 0) {
        return data.detail;
      }
    }
    if (!error.response) {
      return "Backend'e ulaşılamadı. Sunucunun çalıştığından emin olun.";
    }
  }
  return "Geçiş işlemi sırasında beklenmeyen bir hata oluştu.";
}

function TollSimulation({ vehicles, gates, gatesLoading, onRefresh }: Props) {
  const [form] = Form.useForm<TransactionRequest>();
  const [submitting, setSubmitting] = useState(false);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [quote, setQuote] = useState<TariffQuoteResponse | null>(null);
  const [quoteLoading, setQuoteLoading] = useState(false);
  const [quoteError, setQuoteError] = useState<string | null>(null);
  const selectedPlate = Form.useWatch("plate", form);
  const selectedGateId = Form.useWatch("gateId", form);
  const quoteMatchesSelection =
    quote !== null &&
    quote.vehiclePlate === selectedPlate &&
    quote.gateId === selectedGateId;

  useEffect(() => {
    if (!selectedPlate || !selectedGateId) {
      return;
    }

    let cancelled = false;

    const fetchQuote = async () => {
      try {
        const result = await tariffService.getQuote({
          plate: selectedPlate,
          gateId: selectedGateId,
        });

        if (!cancelled) {
          setQuote(result);
        }
      } catch (error) {
        if (!cancelled) {
          setQuoteError(extractErrorMessage(error));
        }
      } finally {
        if (!cancelled) {
          setQuoteLoading(false);
        }
      }
    };

    fetchQuote();

    return () => {
      cancelled = true;
    };
  }, [selectedPlate, selectedGateId]);

  const handleFinish = async (values: TransactionRequest) => {
    if (
      !quote ||
      quote.vehiclePlate !== values.plate ||
      quote.gateId !== values.gateId
    ) {
      setErrorMsg(
        "Seçilen araç ve gişe için güncel tarife henüz hesaplanmadı."
      );
      return;
    }

    setSubmitting(true);
    setErrorMsg(null);
    try {
      const result = await transactionService.createTransaction(values);
      message.success(
        `${result.vehiclePlate} plakalı araç ` +
          `${result.gateName} gişesinden geçti. ` +
          `Kesilen: ${result.fee.toFixed(2)} ₺`
      );
      form.resetFields();
      setQuote(null);
      setQuoteError(null);
      setQuoteLoading(false);
      onRefresh();
    } catch (error) {
      setErrorMsg(extractErrorMessage(error));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div style={{ maxWidth: 480 }}>
      <Space direction="vertical" size="middle" style={{ width: "100%" }}>
        {errorMsg && (
          <Alert
            type="error"
            message="Geçiş başarısız"
            description={errorMsg}
            showIcon
            closable
            onClose={() => setErrorMsg(null)}
          />
        )}

        <Form
          form={form}
          layout="vertical"
          onFinish={handleFinish}
          onValuesChange={(_, values) => {
            setQuote(null);
            setQuoteError(null);
            setQuoteLoading(Boolean(values.plate && values.gateId));
          }}
        >
          <Form.Item
            label="Araç (Plaka)"
            name="plate"
            rules={[{ required: true, message: "Lütfen bir araç seçin." }]}
          >
            <Select
              placeholder="Plaka seçin"
              showSearch
              optionFilterProp="label"
              options={vehicles.map((v) => ({
                value: v.plate,
                label: `${v.plate} — ${v.balance.toFixed(2)} ₺`,
              }))}
            />
          </Form.Item>

          <Form.Item
            label="Gişe"
            name="gateId"
            rules={[
              {
                required: true,
                message: "Lütfen bir gişe seçin.",
              },
            ]}
          >
            <Select
              placeholder="Gişe seçin"
              loading={gatesLoading}
              disabled={gatesLoading}
              options={gates.map((gate) => ({
                value: gate.id,
                label: gate.name,
              }))}
            />
          </Form.Item>

          {quoteLoading && (
            <Alert
              type="info"
              message="Geçiş ücreti hesaplanıyor..."
              showIcon
            />
          )}

          {quoteError && (
            <Alert
              type="error"
              message="Tarife hesaplanamadı"
              description={quoteError}
              showIcon
            />
          )}

          {quoteMatchesSelection && quote && (
            <Alert
              type="success"
              message={`Ödenecek ücret: ${quote.fee.toFixed(2)} ₺`}
              description={`${quote.vehicleClassName} — ${quote.gateName}`}
              showIcon
            />
          )}

          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              loading={submitting}
              disabled={!quoteMatchesSelection || quoteLoading}
              block
            >
              Geçiş Yap
            </Button>
          </Form.Item>
        </Form>
      </Space>
    </div>
  );
}

export default TollSimulation;
