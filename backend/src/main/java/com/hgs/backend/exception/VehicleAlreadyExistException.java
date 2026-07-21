package com.hgs.backend.exception;

import org.springframework.http.HttpStatus;

public class VehicleAlreadyExistException extends BusinessException {
    public VehicleAlreadyExistException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
