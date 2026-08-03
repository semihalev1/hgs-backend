package com.hgs.backend.exception;

import org.springframework.http.HttpStatus;

public class GateNotFoundException extends BusinessException {
    public GateNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
