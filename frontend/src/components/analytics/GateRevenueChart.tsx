import { Typography } from "antd";
import {
  Bar,
  BarChart,
  CartesianGrid,
  LabelList,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import type { GateRevenueResponse } from "../../services/analyticsService";
import { formatCompactCurrency, formatCurrency } from "../../utils/formatters";

interface GateRevenueChartProps {
  data: GateRevenueResponse[];
}

const GateRevenueChart = ({ data }: GateRevenueChartProps) => {
  const chartHeight = Math.max(320, data.length * 56);

  return (
    <section aria-labelledby="gate-revenue-chart-title">
      <Typography.Title
        id="gate-revenue-chart-title"
        level={4}
        style={{ marginTop: 0 }}
      >
        Gişe Bazlı Toplam Gelir
      </Typography.Title>

      <Typography.Paragraph type="secondary">
        Her gişeden tahsil edilen toplam geçiş ücretini gösterir.
      </Typography.Paragraph>

      <div style={{ width: "100%", overflowX: "auto" }}>
        <div style={{ width: "100%", minWidth: 600, height: chartHeight }}>
          <ResponsiveContainer width="100%" height="100%">
          <BarChart
            data={data}
            layout="vertical"
            margin={{
              top: 8,
              right: 130,
              bottom: 8,
              left: 0,
            }}
            accessibilityLayer
          >
            <CartesianGrid strokeDasharray="3 3" horizontal={false} />

            <XAxis
              type="number"
              tickFormatter={(value: number) => formatCompactCurrency(value)}
            />

            <YAxis
              type="category"
              dataKey="gateName"
              width={160}
              tickLine={false}
              tick={{ fontSize: 12 }}
            />

            <Tooltip
              formatter={(value) => [
                formatCurrency(Number(value)),
                "Toplam Gelir",
              ]}
            />

            <Bar
              dataKey="totalRevenue"
              name="Toplam Gelir"
              fill="#1677ff"
              radius={[0, 6, 6, 0]}
              isAnimationActive={false}
            >
              <LabelList
                dataKey="totalRevenue"
                position="right"
                formatter={(value) => formatCurrency(Number(value))}
                fill="#262626"
                fontSize={12}
              />
            </Bar>
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>
    </section>
  );
};

export default GateRevenueChart;
