package com.hgs.backend.dto;

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
public class BalanceUpdateRequest {

    @NotNull(message = "Yeni bakiye boş bırakılamaz.")
    @PositiveOrZero(message = "Yeni bakiye eksi olamaz.")
    private Double newBalance;

}