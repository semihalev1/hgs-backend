package com.hgs.backend.dto;

import com.hgs.backend.validation.ValidPlate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "Plaka boş bırakılamaz.")
    @ValidPlate
    private String plate;

    @NotBlank(message = "Gişe adı boş bırakılamaz.")
    private String stationName;

    @NotNull(message = "Geçiş ücreti boş bırakılamaz.")
    @Positive(message = "Geçiş ücreti sıfırdan büyük olmalıdır.")
    private Double fee;

}