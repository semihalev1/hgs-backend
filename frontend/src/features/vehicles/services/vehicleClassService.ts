import api from "../../../shared/api/api";

export interface VehicleClassResponse {
  id: number;
  code: string;
  name: string;
}

export const vehicleClassService = {
  getAllVehicleClasses: async () => {
    const response = await api.get<VehicleClassResponse[]>("/vehicle-classes");
    return response.data;
  },
};
