import api from "./api";

export interface TransactionRequest {
  plate: string;
  gateId: number;
}

export interface TransactionResponse {
  id: number;
  gateId: number;
  gateCode: string;
  gateName: string;
  fee: number;
  transactionDate: string;
  vehiclePlate: string;
  vehicleClassId: number;
  vehicleClassCode: string;
  vehicleClassName: string;
}

export const transactionService = {
  createTransaction: async (request: TransactionRequest) => {
    const response = await api.post<TransactionResponse>(
      "/transactions",
      request
    );
    return response.data;
  },
  getTransactionsByPlate: async (plate: string) => {
    const response = await api.get<TransactionResponse[]>(
      `/vehicles/${plate}/transactions`
    );
    return response.data;
  },
};
