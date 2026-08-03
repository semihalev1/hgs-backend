import { Typography } from "antd";
import {
  Bar,
  BarChart,
  CartesianGrid,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import type { VehicleClassTrafficResponse } from "../services/analyticsService";
import { formatCount } from "../../../shared/utils/formatters";

interface VehicleClassTrafficChartProps {
  data: VehicleClassTrafficResponse[];
}

const VehicleClassTrafficChart = ({ data }: VehicleClassTrafficChartProps) => {
  return (
    <section aria-labelledby="vehicle-class-traffic-chart-title">
      <Typography.Title
        id="vehicle-class-traffic-chart-title"
        level={4}
        style={{ marginTop: 0 }}
      >
        Araç Sınıfı Geçiş Yoğunluğu
      </Typography.Title>

      <Typography.Paragraph type="secondary">
        Her araç sınıfının gerçekleştirdiği toplam geçiş sayısını gösterir.
      </Typography.Paragraph>

      <div style={{ width: "100%", height: 320 }}>
        <ResponsiveContainer width="100%" height="100%">
          <BarChart
            data={data}
            margin={{
              top: 8,
              right: 16,
              bottom: 8,
              left: 8,
            }}
            accessibilityLayer
          >
            <CartesianGrid strokeDasharray="3 3" vertical={false} />

            <XAxis
              type="category"
              dataKey="vehicleClassName"
              interval={0}
              tickLine={false}
            />

            <YAxis
              type="number"
              allowDecimals={false}
              tickFormatter={(value: number) => formatCount(value)}
            />

            <Tooltip
              formatter={(value) => [
                `${formatCount(Number(value))} geçiş`,
                "Geçiş Sayısı",
              ]}
            />

            <Bar
              dataKey="passageCount"
              name="Geçiş Sayısı"
              fill="#52c41a"
              radius={[6, 6, 0, 0]}
              isAnimationActive={false}
            />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </section>
  );
};

export default VehicleClassTrafficChart;
