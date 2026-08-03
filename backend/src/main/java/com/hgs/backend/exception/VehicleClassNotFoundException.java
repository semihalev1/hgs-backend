package com.hgs.backend.exception;

import org.springframework.http.HttpStatus;

public class VehicleClassNotFoundException extends BusinessException {
    public VehicleClassNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
