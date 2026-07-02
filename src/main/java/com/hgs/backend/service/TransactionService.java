package com.hgs.backend.service;

import com.hgs.backend.dto.TransactionRequest;
import com.hgs.backend.model.Transaction;
import com.hgs.backend.model.Vehicle;
import com.hgs.backend.repository.ITransactionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final ITransactionRepository transactionRepository;
    private final VehicleService vehicleService;

    public TransactionService(ITransactionRepository transactionRepository, VehicleService vehicleService) {
        this.transactionRepository = transactionRepository;
        this.vehicleService = vehicleService;
    }

    // 1. Yeni Geçiş İşlemi (HGS Mantığı)
    public Transaction createTransaction(TransactionRequest request) {
        // 1. Aracı veritabanından bul (Yoksa zaten VehicleService hata fırlatacak)
        Vehicle vehicle = vehicleService.getVehicleByPlate(request.getPlate());

        // 2. Bakiye kontrolü yap
        if (vehicle.getBalance() < request.getFee()) {
            throw new RuntimeException("Yetersiz bakiye! Geçiş reddedildi. Mevcut bakiye: " + vehicle.getBalance());
        }

        // 3. Bakiyeyi düş ve aracı güncelle
        vehicle.setBalance(vehicle.getBalance() - request.getFee());
        vehicleService.addVehicle(vehicle); // Aracı yeni bakiyesiyle tekrar kaydediyoruz

        // 4. Geçiş fişini (Transaction) oluştur ve veritabanına kaydet
        Transaction transaction = new Transaction();
        transaction.setStationName(request.getStationName());
        transaction.setFee(request.getFee());
        transaction.setTransactionDate(LocalDateTime.now()); // O anki saati otomatik alır
        transaction.setVehicle(vehicle); // Bu geçişin hangi araca ait olduğunu bağlarız

        return transactionRepository.save(transaction);
    }

    // 2. Araca Ait Geçmiş Geçişleri Listele
    public List<Transaction> getTransactionsByPlate(String plate) {
        return transactionRepository.findByVehiclePlate(plate);
    }
}