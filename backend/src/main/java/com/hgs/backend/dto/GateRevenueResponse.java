package com.hgs.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GateRevenueResponse {

    private Long gateId;
    private String gateCode;
    private String gateName;
    private BigDecimal totalRevenue;
}