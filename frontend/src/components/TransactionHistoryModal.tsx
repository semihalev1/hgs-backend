import { useEffect, useState } from "react";
import { Modal, Table, message } from "antd";
import type { ColumnsType } from "antd/es/table";
import {
  transactionService,
  type TransactionResponse,
} from "../services/transactionService";

interface Props {
  plate: string | null;
  open: boolean;
  onClose: () => void;
}

const dateFormatter = new Intl.DateTimeFormat("tr-TR", {
  day: "2-digit",
  month: "long",
  year: "numeric",
  hour: "2-digit",
  minute: "2-digit",
});

const formatDate = (value: string): string => {
  const date = new Date(value);
  return isNaN(date.getTime()) ? value : dateFormatter.format(date);
};

const columns: ColumnsType<TransactionResponse> = [
  {
    title: "Geçiş Tarihi",
    dataIndex: "transactionDate",
    key: "transactionDate",
    render: (value: string) => formatDate(value),
  },
  {
    title: "Gişe",
    dataIndex: "stationName",
    key: "stationName",
  },
  {
    title: "Ücret",
    dataIndex: "fee",
    key: "fee",
    align: "right",
    render: (value: number) => `${value.toFixed(2)} ₺`,
  },
];

export default function TransactionHistoryModal({
  plate,
  open,
  onClose,
}: Props) {
  const [transactions, setTransactions] = useState<TransactionResponse[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!open || !plate) return;

    let cancelled = false;

    const fetchHistory = async () => {
      try {
        setLoading(true);
        const data = await transactionService.getTransactionsByPlate(plate);
        if (!cancelled) setTransactions(data);
      } catch {
        if (!cancelled) {
          message.error("Geçiş geçmişi yüklenemedi.");
          setTransactions([]);
        }
      } finally {
        if (!cancelled) setLoading(false);
      }
    };

    fetchHistory();

    return () => {
      cancelled = true;
    };
  }, [open, plate]);

  return (
    <Modal
      title={plate ? `Geçiş Geçmişi — ${plate}` : "Geçiş Geçmişi"}
      open={open}
      onCancel={onClose}
      footer={null}
      width={640}
      destroyOnClose
    >
      <Table
        dataSource={transactions}
        columns={columns}
        rowKey="id"
        loading={loading}
        size="small"
        pagination={{ pageSize: 5, hideOnSinglePage: true }}
        locale={{ emptyText: "Bu araca ait geçiş kaydı bulunamadı." }}
      />
    </Modal>
  );
}
