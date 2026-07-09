package com.hgs.backend.dto;

import com.hgs.backend.validation.ValidPlate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {

    @NotBlank(message = "Plaka boş bırakılamaz.")
    @ValidPlate
    private String plate;

    @NotBlank(message = "Araç sınıfı boş bırakılamaz.")
    private String vehicleClass;

    @NotNull(message = "Bakiye boş bırakılamaz.")
    @PositiveOrZero(message = "Bakiye eksi olamaz.")
    private Double balance;

    @NotBlank(message = "Araç sahibi boş bırakılamaz.")
    private String ownerName;

}