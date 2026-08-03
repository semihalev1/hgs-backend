import api from "./api";

export interface TariffQuoteRequest {
  plate: string;
  gateId: number;
}

export interface TariffQuoteResponse {
  vehiclePlate: string;
  vehicleClassId: number;
  vehicleClassCode: string;
  vehicleClassName: string;
  gateId: number;
  gateCode: string;
  gateName: string;
  fee: number;
}

export const tariffService = {
  getQuote: async (request: TariffQuoteRequest) => {
    const response = await api.get<TariffQuoteResponse>("/tariffs/quote", {
      params: request,
    });

    return response.data;
  },
};
