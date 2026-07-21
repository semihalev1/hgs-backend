import { useState } from "react";
import { Form, Input, InputNumber, Button, Alert } from "antd";
import axios from "axios";
import {
  vehicleService,
  type VehicleRequest,
} from "../services/vehicleService";

interface Props {
  onVehicleAdded: () => void;
}

interface ProblemDetailResponse {
  type?: string;
  title?: string;
  status?: number;
  detail?: string;
  hatalar?: string[];
}

const PLATE_REGEX = /^(0[1-9]|[1-7][0-9]|8[0-1])[A-Z]{1,3}[0-9]{2,4}$/;

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

  return "Araç eklenirken beklenmeyen bir hata oluştu.";
}

export default function VehicleForm({ onVehicleAdded }: Props) {
  const [form] = Form.useForm<VehicleRequest>();
  const [submitting, setSubmitting] = useState(false);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);

  const handleFinish = async (values: VehicleRequest) => {
    setSubmitting(true);
    setErrorMsg(null);
    try {
      await vehicleService.createVehicle(values);
      form.resetFields();
      onVehicleAdded();
    } catch (error) {
      setErrorMsg(extractErrorMessage(error));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div style={{ maxWidth: 480 }}>
      {errorMsg && (
        <Alert
          type="error"
          message="Araç eklenemedi"
          description={errorMsg}
          showIcon
          closable
          onClose={() => setErrorMsg(null)}
          style={{ marginBottom: 16 }}
        />
      )}

      <Form form={form} layout="vertical" onFinish={handleFinish}>
        <Form.Item
          label="Plaka"
          name="plate"
          normalize={(value: string) => value?.toUpperCase().replace(/\s/g, "")}
          rules={[
            { required: true, message: "Plaka alanı zorunludur." },
            {
              pattern: PLATE_REGEX,
              message: "Geçerli bir plaka giriniz (örn: 34ABC123, boşluksuz).",
            },
          ]}
        >
          <Input placeholder="34ABC123" />
        </Form.Item>

        <Form.Item
          label="Araç Sınıfı"
          name="vehicleClass"
          rules={[{ required: true, message: "Araç sınıfı zorunludur." }]}
        >
          <Input placeholder="Örn: Otomobil, Kamyon..." />
        </Form.Item>

        <Form.Item
          label="Bakiye"
          name="balance"
          initialValue={0}
          rules={[{ required: true, message: "Bakiye zorunludur." }]}
        >
          <InputNumber min={0} style={{ width: "100%" }} addonAfter="₺" />
        </Form.Item>

        <Form.Item
          label="Araç Sahibi"
          name="ownerName"
          rules={[{ required: true, message: "Araç sahibi zorunludur." }]}
        >
          <Input placeholder="Ad Soyad" />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={submitting} block>
            Aracı Ekle
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
}
