package com.hgs.backend.dto.tariff;

import com.hgs.backend.validation.ValidPlate;
import jakarta.validation.constraints.NotBlank;
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
public class TariffQuoteRequest {

    @NotBlank(message = "Plaka boş bırakılamaz.")
    @ValidPlate
    private String plate;

    @NotNull(message = "Gişe boş bırakılamaz.")
    @Positive(message = "Gişe kimliği pozitif olmalıdır.")
    private Long gateId;

}
