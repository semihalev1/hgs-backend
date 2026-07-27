import { useState } from "react";
import { Button, Table, Space, Popconfirm, message } from "antd";
import type { ColumnsType } from "antd/es/table";
import {
  vehicleService,
  type VehicleResponse,
} from "../services/vehicleService";
import VehicleForm from "./VehicleForm";
import LoadBalanceModal from "./LoadBalanceModal";
import TransactionHistoryModal from "./TransactionHistoryModal";

interface Props {
  vehicles: VehicleResponse[];
  loading: boolean;
  onRefresh: () => void;
}

const getColumns = (
  onDelete: (plate: string) => void,
  onLoadBalance: (vehicle: VehicleResponse) => void,
  onShowHistory: (plate: string) => void
): ColumnsType<VehicleResponse> => [
  {
    title: "Plaka",
    dataIndex: "plate",
    key: "plate",
    render: (text: string) => <b>{text}</b>,
  },
  { title: "Sahibi", dataIndex: "ownerName", key: "ownerName" },
  { title: "Araç sınıfı", dataIndex: "vehicleClass", key: "vehicleClass" },
  {
    title: "Bakiye",
    dataIndex: "balance",
    key: "balance",
    render: (text: number) => <b>{text.toFixed(2)} ₺</b>,
  },
  {
    title: "İşlemler",
    key: "actions",
    render: (_, record) => (
      <Space size="small">
        <Button type="link" onClick={() => onLoadBalance(record)}>
          Bakiye Yükle
        </Button>
        <Button type="link" onClick={() => onShowHistory(record.plate)}>
          Geçiş Geçmişi
        </Button>
        <Popconfirm
          title="Aracı sil"
          description={`${record.plate} plakalı aracı sistemden silmek istediğinize emin misiniz?`}
          onConfirm={() => onDelete(record.plate)}
          okText="Evet, Sil"
          cancelText="Vazgeç"
          okButtonProps={{ danger: true }}
        >
          <Button type="link" danger>
            Sil
          </Button>
        </Popconfirm>
      </Space>
    ),
  },
];

function VehicleManagement({ vehicles, loading, onRefresh }: Props) {
  const [selectedVehicle, setSelectedVehicle] =
    useState<VehicleResponse | null>(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [historyPlate, setHistoryPlate] = useState<string | null>(null);
  const [historyOpen, setHistoryOpen] = useState(false);

  const handleDelete = async (plate: string) => {
    try {
      await vehicleService.deleteVehicle(plate);
      message.success(`${plate} plakalı araç başarıyla silindi.`);
      onRefresh();
    } catch {
      message.error("Araç silinemedi.");
    }
  };

  const handleOpenModal = (vehicle: VehicleResponse) => {
    setSelectedVehicle(vehicle);
    setModalOpen(true);
  };

  const handleCloseModal = () => {
    setModalOpen(false);
    setSelectedVehicle(null);
  };

  const handleShowHistory = (plate: string) => {
    setHistoryPlate(plate);
    setHistoryOpen(true);
  };

  const handleCloseHistory = () => {
    setHistoryOpen(false);
    setHistoryPlate(null);
  };

  return (
    <Space direction="vertical" size="large" style={{ width: "100%" }}>
      <VehicleForm onVehicleAdded={onRefresh} />
      <Table
        dataSource={vehicles}
        columns={getColumns(handleDelete, handleOpenModal, handleShowHistory)}
        rowKey="id"
        loading={loading}
        bordered
      />
      <LoadBalanceModal
        vehicle={selectedVehicle}
        open={modalOpen}
        onClose={handleCloseModal}
        onSuccess={onRefresh}
      />
      <TransactionHistoryModal
        plate={historyPlate}
        open={historyOpen}
        onClose={handleCloseHistory}
      />
    </Space>
  );
}

export default VehicleManagement;
