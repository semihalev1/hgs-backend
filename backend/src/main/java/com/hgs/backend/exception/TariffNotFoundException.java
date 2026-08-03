package com.hgs.backend.exception;

import org.springframework.http.HttpStatus;

public class TariffNotFoundException extends BusinessException {
    public TariffNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
