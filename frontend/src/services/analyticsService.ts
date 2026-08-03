import api from "./api";

export interface GateRevenueResponse {
  gateId: number;
  gateCode: string;
  gateName: string;
  totalRevenue: number;
}

export interface VehicleClassTrafficResponse {
  vehicleClassId: number;
  vehicleClassCode: string;
  vehicleClassName: string;
  passageCount: number;
}

export const analyticsService = {
  getGateRevenue: async (signal?: AbortSignal) => {
    const response = await api.get<GateRevenueResponse[]>(
      "/analytics/gate-revenue",
      { signal }
    );
    return response.data;
  },

  getVehicleClassTraffic: async (signal?: AbortSignal) => {
    const response = await api.get<VehicleClassTrafficResponse[]>(
      "/analytics/vehicle-class-traffic",
      { signal }
    );
    return response.data;
  },
};
