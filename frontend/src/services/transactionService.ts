import api from "./api";

export interface TransactionRequest {
  plate: string;
  stationName: string;
  fee: number;
}

export interface TransactionResponse {
  id: number;
  stationName: string;
  fee: number;
  transactionDate: string;
  vehiclePlate: string;
}

export const transactionService = {
  createTransaction: async (request: TransactionRequest) => {
    const response = await api.post<TransactionResponse>(
      "/transaction",
      request
    );
    return response.data;
  },
  getTransactionsByPlate: async (plate: string) => {
    const response = await api.get<TransactionResponse[]>(
      `/vehicles/${plate}/transaction`
    );
    return response.data;
  },
};
