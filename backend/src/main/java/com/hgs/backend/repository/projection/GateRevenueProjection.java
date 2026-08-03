package com.hgs.backend.repository.projection;

import java.math.BigDecimal;

public interface GateRevenueProjection {

    Long getGateId();

    String getGateCode();

    String getGateName();

    BigDecimal getTotalRevenue();
}