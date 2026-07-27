import { useState } from "react";
import { Form, Select, InputNumber, Button, Alert, Space, message } from "antd";
import axios from "axios";
import { type VehicleResponse } from "../services/vehicleService";
import {
  transactionService,
  type TransactionRequest,
} from "../services/transactionService";

interface Props {
  vehicles: VehicleResponse[];
  onRefresh: () => void;
}

interface ProblemDetailResponse {
  detail?: string;
  hatalar?: string[];
}

const TOLL_GATES = [
  "Çamlıca Gişesi",
  "FSM Köprüsü",
  "15 Temmuz Şehitler Köprüsü",
  "Yavuz Sultan Selim Köprüsü",
  "Osmangazi Köprüsü",
  "Avrasya Tüneli",
];

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

function TollSimulation({ vehicles, onRefresh }: Props) {
  const [form] = Form.useForm<TransactionRequest>();
  const [submitting, setSubmitting] = useState(false);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);

  const handleFinish = async (values: TransactionRequest) => {
    setSubmitting(true);
    setErrorMsg(null);
    try {
      const result = await transactionService.createTransaction(values);
      message.success(
        `${result.vehiclePlate} plakalı araç ${values.stationName} gişesinden geçti. ` +
          `Kesilen: ${values.fee.toFixed(2)} ₺`
      );
      form.resetFields();
      // Bakiyeler değişti; App'teki tek listeyi tazele → her iki sekme de güncellenir
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

        <Form form={form} layout="vertical" onFinish={handleFinish}>
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
            name="stationName"
            rules={[{ required: true, message: "Lütfen bir gişe seçin." }]}
          >
            <Select
              placeholder="Gişe seçin"
              options={TOLL_GATES.map((gate) => ({
                value: gate,
                label: gate,
              }))}
            />
          </Form.Item>

          <Form.Item
            label="Geçiş Ücreti"
            name="fee"
            rules={[
              { required: true, message: "Geçiş ücreti zorunludur." },
              {
                type: "number",
                min: 1,
                message: "Ücret 0'dan büyük olmalıdır.",
              },
            ]}
          >
            <InputNumber
              min={1}
              style={{ width: "100%" }}
              addonAfter="₺"
              placeholder="Örn: 45"
            />
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" loading={submitting} block>
              Geçiş Yap
            </Button>
          </Form.Item>
        </Form>
      </Space>
    </div>
  );
}

export default TollSimulation;
