import api from "./api";

export interface VehicleResponse {
  id: number;
  plate: string;
  vehicleClass: string;
  balance: number;
  ownerName: string;
}

export interface VehicleRequest {
  plate: string;
  vehicleClass: string;
  balance: number;
  ownerName: string;
}

export interface BalanceUpdateRequest {
  newBalance: number;
}

export const vehicleService = {
  getAllVehicles: async () => {
    const response = await api.get<VehicleResponse[]>("/vehicles");
    return response.data;
  },

  createVehicle: async (newVehicle: VehicleRequest) => {
    const response = await api.post<VehicleResponse>("/vehicles", newVehicle);
    return response.data;
  },

  deleteVehicle: async (plate: string) => {
    const response = await api.delete(`/vehicles/${plate}`);
    return response.data;
  },
};
