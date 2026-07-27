import { useEffect, useState, useCallback } from "react";
import { Tabs, Typography, Space, message } from "antd";
import type { TabsProps } from "antd";
import {
  vehicleService,
  type VehicleResponse,
} from "./services/vehicleService";
import VehicleManagement from "./components/VehicleManagement";
import TollSimulation from "./components/TollSimulation";

const { Title } = Typography;

function App() {
  const [vehicles, setVehicles] = useState<VehicleResponse[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  const fetchVehicles = useCallback(async () => {
    try {
      setLoading(true);
      const data = await vehicleService.getAllVehicles();
      setVehicles(data);
    } catch {
      message.error("Araçlar yüklenirken backend'e ulaşılamadı.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchVehicles();
  }, [fetchVehicles]);

  const items: TabsProps["items"] = [
    {
      key: "vehicles",
      label: "Araç Yönetimi",
      children: (
        <VehicleManagement
          vehicles={vehicles}
          loading={loading}
          onRefresh={fetchVehicles}
        />
      ),
    },
    {
      key: "simulation",
      label: "HGS Geçiş Simülasyonu",
      children: (
        <TollSimulation vehicles={vehicles} onRefresh={fetchVehicles} />
      ),
    },
  ];

  return (
    <div style={{ padding: "50px" }}>
      <Space direction="vertical" size="large" style={{ width: "100%" }}>
        <Title level={2}>HGS Yönetim Paneli</Title>
        <Tabs defaultActiveKey="vehicles" items={items} />
      </Space>
    </div>
  );
}

export default App;
