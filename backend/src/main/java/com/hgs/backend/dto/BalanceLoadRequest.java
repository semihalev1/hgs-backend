package com.hgs.backend.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceLoadRequest {

    @NotNull(message = "Yüklenecek tutar boş bırakılamaz.")
    @Positive(message = "Yüklenecek tutar sıfırdan büyük olmalıdır.")
    @Digits(
            integer = 10,
            fraction = 2,
            message = "Tutar en fazla 10 tam ve 2 ondalık basamak içerebilir."
    )
    private BigDecimal amount;

}