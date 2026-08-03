package com.hgs.backend.dto.gate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GateResponse {

    private Long id;
    private String code;
    private String name;

}
