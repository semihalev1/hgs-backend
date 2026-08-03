import api from "../../../shared/api/api";

export interface GateResponse {
  id: number;
  code: string;
  name: string;
}

export const gateService = {
  getAllGates: async () => {
    const response = await api.get<GateResponse[]>("/gates");
    return response.data;
  },
};
