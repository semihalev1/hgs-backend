package com.hgs.backend.util;

import com.hgs.backend.dto.GateResponse;
import com.hgs.backend.model.Gate;
import org.springframework.stereotype.Component;

@Component
public class GateHelper {

    public GateResponse convertToResponse(Gate gate) {
        GateResponse gateResponse = new GateResponse();
        gateResponse.setId(gate.getId());
        gateResponse.setCode(gate.getCode());
        gateResponse.setName(gate.getName());

        return gateResponse;
    }

}
