package com.hgs.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceLoadRequest {

    @NotNull(message = "Yüklenecek tutar boş bırakılamaz.")
    @Positive(message = "Yüklenecek tutar 0'dan büyük olmalıdır.")
    private Double amount;

}