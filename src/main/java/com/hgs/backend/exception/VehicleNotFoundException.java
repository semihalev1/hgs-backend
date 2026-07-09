package com.hgs.backend.exception;

import org.springframework.http.HttpStatus;

public class VehicleNotFoundException extends BusinessException {
    public VehicleNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
