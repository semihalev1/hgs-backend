package com.hgs.backend.service;

import com.hgs.backend.model.Vehicle;
import com.hgs.backend.repository.IVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    // Depo sorumlumuzu buraya çağırıyoruz
    private final IVehicleRepository vehicleRepository;

    // Constructor Injection (Bağımlılık Enjeksiyonu)
    public VehicleService(IVehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // 1. Yeni Araç Kaydetme Metodu
    public Vehicle addVehicle(Vehicle vehicle) {
        // İleride buraya "Bu plaka önceden kayıtlı mı?" gibi kontroller ekleyeceğiz.
        return vehicleRepository.save(vehicle);
    }

    // 2. Plakaya Göre Araç Bulma Metodu
    public Vehicle getVehicleByPlate(String plate) {
        // Optional kutusunu açıyoruz, araç yoksa hata fırlatıyoruz
        return vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new RuntimeException("Sistemde bu plakaya ait bir araç bulunamadı: " + plate));
    }

    // 3. Tüm Araçları Listeleme Metodu
    public List<Vehicle> getAllVehicles() {
        // findAll() metodu arka planda "SELECT * FROM vehicles" SQL kodunu çalıştırır.
        return vehicleRepository.findAll();
    }

    // 4. PUT: Aracın Tüm Bilgilerini Güncelleme
    public Vehicle updateVehicle(String plate, Vehicle updatedVehicleData) {
        // Önce güncellenecek aracı veritabanında buluyoruz
        Vehicle existingVehicle = getVehicleByPlate(plate);

        // id ve plaka DEĞİŞMEDEN, diğer tüm alanları üzerine yazıyoruz (PUT mantığı)
        existingVehicle.setOwnerName(updatedVehicleData.getOwnerName());
        existingVehicle.setVehicleClass(updatedVehicleData.getVehicleClass());
        existingVehicle.setBalance(updatedVehicleData.getBalance());

        return vehicleRepository.save(existingVehicle);
    }

    // 5. PATCH: Sadece Bakiye Güncelleme
    public Vehicle updateBalance(String plate, Double newBalance) {
        Vehicle existingVehicle = getVehicleByPlate(plate);

        // Sadece tek bir alanı kısmi olarak güncelliyoruz (PATCH mantığı)
        existingVehicle.setBalance(newBalance);

        return vehicleRepository.save(existingVehicle);
    }

    // 6. DELETE: Aracı Sistemden Silme
    @org.springframework.transaction.annotation.Transactional
    public void deleteVehicle(String plate) {
        Vehicle existingVehicle = getVehicleByPlate(plate);
        vehicleRepository.delete(existingVehicle);
    }
}