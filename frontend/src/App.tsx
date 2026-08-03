import { lazy, Suspense, useCallback, useEffect, useState } from "react";
import { Tabs, Typography, Space, Spin, message } from "antd";
import type { TabsProps } from "antd";
import {
  vehicleService,
  type VehicleResponse,
} from "./features/vehicles/services/vehicleService";
import VehicleManagement from "./features/vehicles/components/VehicleManagement";
import TollSimulation from "./features/simulation/components/TollSimulation";
import {
  gateService,
  type GateResponse,
} from "./features/simulation/services/gateService";

import {
  vehicleClassService,
  type VehicleClassResponse,
} from "./features/vehicles/services/vehicleClassService";

const AnalyticsDashboard = lazy(
  () => import("./features/analytics/components/AnalyticsDashboard")
);
const { Title } = Typography;

function App() {
  const [vehicles, setVehicles] = useState<VehicleResponse[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [gates, setGates] = useState<GateResponse[]>([]);
  const [vehicleClasses, setVehicleClasses] = useState<VehicleClassResponse[]>(
    []
  );
  const [referencesLoading, setReferencesLoading] = useState(true);

  const fetchVehicles = useCallback(async () => {
    try {
      const data = await vehicleService.getAllVehicles();
      setVehicles(data);
    } catch {
      message.error("Araçlar yüklenirken backend'e ulaşılamadı.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    let cancelled = false;

    void vehicleService
      .getAllVehicles()
      .then((data) => {
        if (!cancelled) {
          setVehicles(data);
        }
      })
      .catch(() => {
        if (!cancelled) {
          message.error("Araçlar yüklenirken backend'e ulaşılamadı.");
        }
      })
      .finally(() => {
        if (!cancelled) {
          setLoading(false);
        }
      });

    void Promise.all([
      gateService.getAllGates(),
      vehicleClassService.getAllVehicleClasses(),
    ])
      .then(([gateData, vehicleClassData]) => {
        if (!cancelled) {
          setGates(gateData);
          setVehicleClasses(vehicleClassData);
        }
      })
      .catch(() => {
        if (!cancelled) {
          message.error("Gişe ve araç sınıfı bilgileri yüklenemedi.");
        }
      })
      .finally(() => {
        if (!cancelled) {
          setReferencesLoading(false);
        }
      });

    return () => {
      cancelled = true;
    };
  }, []);

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
    {
      key: "analytics",
      label: "Analiz ve Raporlama",
      destroyOnHidden: true,
      children: (
        <Suspense
          fallback={
            <div
              style={{
                minHeight: 320,
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
              }}
            >
              <Spin size="large" tip="Analiz ekranı yükleniyor..." />
            </div>
          }
        >
          <AnalyticsDashboard />
        </Suspense>
      ),
    },
  ];

  return (
    <div style={{ padding: "clamp(16px, 4vw, 50px)" }}>
      <Space direction="vertical" size="large" style={{ width: "100%" }}>
        <Title level={2}>HGS Yönetim Paneli</Title>
        <Tabs defaultActiveKey="vehicles" items={items} />
      </Space>
    </div>
  );
}

export default App;
