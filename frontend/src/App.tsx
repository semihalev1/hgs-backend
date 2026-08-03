import { useEffect, useState, useCallback } from "react";
import { Tabs, Typography, Space, message } from "antd";
import type { TabsProps } from "antd";
import {
  vehicleService,
  type VehicleResponse,
} from "./services/vehicleService";
import VehicleManagement from "./components/VehicleManagement";
import TollSimulation from "./components/TollSimulation";
import { gateService, type GateResponse } from "./services/gateService";

import {
  vehicleClassService,
  type VehicleClassResponse,
} from "./services/vehicleClassService";

const { Title } = Typography;

function App() {
  const [vehicles, setVehicles] = useState<VehicleResponse[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [gates, setGates] = useState<GateResponse[]>([]);
  const [vehicleClasses, setVehicleClasses] = useState<VehicleClassResponse[]>(
    []
  );
  const [referencesLoading, setReferencesLoading] = useState(true);

  const fetchReferenceData = useCallback(async () => {
    try {
      setReferencesLoading(true);

      const [gateData, vehicleClassData] = await Promise.all([
        gateService.getAllGates(),
        vehicleClassService.getAllVehicleClasses(),
      ]);

      setGates(gateData);
      setVehicleClasses(vehicleClassData);
    } catch {
      message.error("Gişe ve araç sınıfı bilgileri yüklenemedi.");
    } finally {
      setReferencesLoading(false);
    }
  }, []);

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
    fetchReferenceData();
  }, [fetchVehicles, fetchReferenceData]);

  const items: TabsProps["items"] = [
    {
      key: "vehicles",
      label: "Araç Yönetimi",
      children: (
        <VehicleManagement
          vehicles={vehicles}
          vehicleClasses={vehicleClasses}
          loading={loading}
          referencesLoading={referencesLoading}
          onRefresh={fetchVehicles}
        />
      ),
    },
    {
      key: "simulation",
      label: "HGS Geçiş Simülasyonu",
      children: (
        <TollSimulation
          vehicles={vehicles}
          gates={gates}
          gatesLoading={referencesLoading}
          onRefresh={fetchVehicles}
        />
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
