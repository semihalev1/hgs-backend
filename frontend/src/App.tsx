import { useEffect, useState } from "react";
import { Button, Table, Typography, Space, Popconfirm, message } from "antd";
import {
  vehicleService,
  type VehicleResponse,
} from "./services/vehicleService";
import VehicleForm from "./components/VehicleForm";

const { Title } = Typography;

function App() {
  const [vehicles, setVehicles] = useState<VehicleResponse[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  const fetchVehicles = async () => {
    try {
      setLoading(true);
      const data = await vehicleService.getAllVehicles();
      setVehicles(data);
    } catch (error) {
      message.error("Araçlar yüklenirken backend'e ulaşılamadı.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchVehicles();
  }, []);

  const handleDelete = async (plate: string) => {
    try {
      await vehicleService.deleteVehicle(plate);
      message.success(`${plate} plakalı araç başarıyla silindi.`);
      await fetchVehicles();
    } catch {
      message.error("Araç silinemedi.");
    }
  };

  const columns = [
    {
      title: "Plaka",
      dataIndex: "plate",
      key: "plate",
      fontWeight: "bold",
    },
    {
      title: "Sahibi",
      dataIndex: "ownerName",
      key: "ownerName",
    },
    {
      title: "Araç sınıfı",
      dataIndex: "vehicleClass",
      key: "vehicleClass",
    },
    {
      title: "Bakiye",
      dataIndex: "balance",
      key: "balance",
      render: (text: number) => <b>{text.toFixed(2)} ₺</b>,
    },
    {
      title: "İşlemler",
      key: "actions",
      render: (_: any, record: VehicleResponse) => (
        <Popconfirm
          title="Aracı sil"
          description={`${record.plate} plakalı aracı sistemden silmek istediğinize emin misiniz?`}
          onConfirm={() => handleDelete(record.plate)}
          okText="Evet, Sil"
          cancelText="Vazgeç"
          okButtonProps={{ danger: true }}
        >
          <Button type="link" danger>
            Sil
          </Button>
        </Popconfirm>
      ),
    },
  ];

  return (
    <div style={{ padding: "50px" }}>
      <Space direction="vertical" size="large" style={{ width: "100%" }}>
        <Title level={2}>HGS Yönetim Paneli</Title>
        <VehicleForm onVehicleAdded={fetchVehicles} />
        <Table
          dataSource={vehicles}
          columns={columns}
          rowKey="id"
          loading={loading}
          bordered
        />
      </Space>
    </div>
  );
}

export default App;
