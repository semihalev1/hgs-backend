import { useState } from "react";
import { Modal, InputNumber, Form, message } from "antd";
import axios from "axios";
import {
  vehicleService,
  type VehicleResponse,
} from "../services/vehicleService";

interface Props {
  vehicle: VehicleResponse | null;
  open: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

interface FormValues {
  amount: number;
}

export default function LoadBalanceModal({
  vehicle,
  open,
  onClose,
  onSuccess,
}: Props) {
  const [form] = Form.useForm<FormValues>();
  const [submitting, setSubmitting] = useState(false);

  const handleOk = async () => {
    try {
      const values = await form.validateFields();
      setSubmitting(true);
      if (!vehicle) return;
      await vehicleService.loadBalance(vehicle.plate, values.amount);
      message.success(
        `${vehicle.plate} plakalı araca ${values.amount.toFixed(2)} ₺ yüklendi.`
      );
      form.resetFields();
      onSuccess();
      onClose();
    } catch (error) {
      if (axios.isAxiosError(error)) {
        message.error("Bakiye yüklenemedi. Lütfen tekrar deneyin.");
      }
    } finally {
      setSubmitting(false);
    }
  };

  const handleCancel = () => {
    form.resetFields();
    onClose();
  };

  return (
    <Modal
      title={vehicle ? `Bakiye Yükle — ${vehicle.plate}` : "Bakiye Yükle"}
      open={open}
      onOk={handleOk}
      onCancel={handleCancel}
      okText="Yükle"
      cancelText="Vazgeç"
      confirmLoading={submitting}
      destroyOnClose
    >
      {vehicle && (
        <p style={{ marginBottom: 16 }}>
          Mevcut bakiye: <b>{vehicle.balance.toFixed(2)} ₺</b>
        </p>
      )}
      <Form form={form} layout="vertical">
        <Form.Item
          label="Yüklenecek Tutar"
          name="amount"
          rules={[
            { required: true, message: "Tutar zorunludur." },
            {
              type: "number",
              min: 1,
              message: "Tutar 0'dan büyük olmalıdır.",
            },
          ]}
        >
          <InputNumber
            min={1}
            style={{ width: "100%" }}
            addonAfter="₺"
            placeholder="Örn: 100"
            autoFocus
          />
        </Form.Item>
      </Form>
    </Modal>
  );
}
