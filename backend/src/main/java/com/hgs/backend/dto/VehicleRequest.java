package com.hgs.backend.dto;

import com.hgs.backend.validation.ValidPlate;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {

    @NotBlank(message = "Plaka boş bırakılamaz.")
    @ValidPlate
    private String plate;

    @NotNull(message = "Araç sınıfı boş bırakılamaz.")
    private Long vehicleClassId;

    @NotNull(message = "Bakiye boş bırakılamaz.")
    @PositiveOrZero(message = "Bakiye negatif olamaz.")
    @Digits(
            integer = 10,
            fraction = 2,
            message = "Bakiye en fazla 10 tam ve 2 ondalık basamak içerebilir."
    )
    private BigDecimal balance;

    @NotBlank(message = "Araç sahibi boş bırakılamaz.")
    private String ownerName;

}