import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import {
  Alert,
  Button,
  Card,
  Col,
  Empty,
  Row,
  Space,
  Spin,
  Statistic,
  Typography,
} from "antd";
import {
  analyticsService,
  type GateRevenueResponse,
  type VehicleClassTrafficResponse,
} from "../../services/analyticsService";
import { formatCount, formatCurrency } from "../../utils/formatters";
import GateRevenueChart from "./GateRevenueChart";
import VehicleClassTrafficChart from "./VehicleClassTrafficChart";

const AnalyticsDashboard = () => {
  const [gateRevenue, setGateRevenue] = useState<GateRevenueResponse[]>([]);
  const [vehicleClassTraffic, setVehicleClassTraffic] = useState<
    VehicleClassTrafficResponse[]
  >([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const activeRequestRef = useRef<AbortController | null>(null);

  const fetchAnalytics = useCallback((signal: AbortSignal) => {
    return Promise.all([
      analyticsService.getGateRevenue(signal),
      analyticsService.getVehicleClassTraffic(signal),
    ]);
  }, []);

  useEffect(() => {
    const controller = new AbortController();
    activeRequestRef.current = controller;

    void fetchAnalytics(controller.signal)
      .then(([gateRevenueData, vehicleClassTrafficData]) => {
        if (controller.signal.aborted) {
          return;
        }

        setGateRevenue(gateRevenueData);
        setVehicleClassTraffic(vehicleClassTrafficData);
      })
      .catch(() => {
        if (!controller.signal.aborted) {
          setError("Analiz verileri yüklenirken bir hata oluştu.");
        }
      })
      .finally(() => {
        if (!controller.signal.aborted) {
          setLoading(false);
        }
      });

    return () => {
      controller.abort();
    };
  }, [fetchAnalytics]);

  const loadAnalytics = useCallback(async () => {
    activeRequestRef.current?.abort();

    const controller = new AbortController();
    activeRequestRef.current = controller;
    setLoading(true);
    setError(null);

    try {
      const [gateRevenueData, vehicleClassTrafficData] = await fetchAnalytics(
        controller.signal
      );

      if (!controller.signal.aborted) {
        setGateRevenue(gateRevenueData);
        setVehicleClassTraffic(vehicleClassTrafficData);
      }
    } catch {
      if (!controller.signal.aborted) {
        setError("Analiz verileri yüklenirken bir hata oluştu.");
      }
    } finally {
      if (!controller.signal.aborted) {
        setLoading(false);
      }
    }
  }, [fetchAnalytics]);

  const totalRevenue = useMemo(
    () => gateRevenue.reduce((total, gate) => total + gate.totalRevenue, 0),
    [gateRevenue]
  );

  const totalPassages = useMemo(
    () =>
      vehicleClassTraffic.reduce(
        (total, vehicleClass) => total + vehicleClass.passageCount,
        0
      ),
    [vehicleClassTraffic]
  );

  if (loading) {
    return (
      <div
        style={{
          minHeight: 320,
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <Spin size="large" tip="Analiz verileri yükleniyor..." />
      </div>
    );
  }

  if (error) {
    return (
      <Alert
        type="error"
        showIcon
        message="Analiz verileri alınamadı"
        description={error}
        action={
          <Button onClick={() => void loadAnalytics()}>Tekrar Dene</Button>
        }
      />
    );
  }

  if (gateRevenue.length === 0 && vehicleClassTraffic.length === 0) {
    return (
      <Empty description="Gösterilecek analiz verisi bulunamadı.">
        <Button onClick={() => void loadAnalytics()}>Yenile</Button>
      </Empty>
    );
  }

  return (
    <Space direction="vertical" size="large" style={{ width: "100%" }}>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          gap: 16,
          flexWrap: "wrap",
        }}
      >
        <div>
          <Typography.Title level={2} style={{ marginBottom: 4 }}>
            Analiz ve Raporlama
          </Typography.Title>

          <Typography.Text type="secondary">
            Sistem genelindeki gelir ve geçiş yoğunluğu istatistikleri
          </Typography.Text>
        </div>

        <Button onClick={() => void loadAnalytics()}>Yenile</Button>
      </div>

      <Row gutter={[16, 16]}>
        <Col xs={24} md={12}>
          <Card>
            <Statistic
              title="Toplam Gelir"
              value={totalRevenue}
              formatter={(value) => formatCurrency(Number(value))}
            />
          </Card>
        </Col>

        <Col xs={24} md={12}>
          <Card>
            <Statistic
              title="Toplam Geçiş"
              value={totalPassages}
              formatter={(value) => formatCount(Number(value))}
              suffix="geçiş"
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[16, 16]} align="stretch">
        <Col xs={24} xl={12}>
          <Card style={{ height: "100%" }}>
            <GateRevenueChart data={gateRevenue} />
          </Card>
        </Col>

        <Col xs={24} xl={12}>
          <Card style={{ height: "100%" }}>
            <VehicleClassTrafficChart data={vehicleClassTraffic} />
          </Card>
        </Col>
      </Row>
    </Space>
  );
};

export default AnalyticsDashboard;
