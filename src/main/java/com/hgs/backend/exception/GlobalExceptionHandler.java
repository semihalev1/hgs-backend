package com.hgs.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(BusinessException ex) {
        log.warn("İş kuralı hatası yakalandı: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(ex.getHttpStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        log.error("Sistemde beklenmeyen hata!", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Sistemde beklenmeyen hata!");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException (MethodArgumentNotValidException ex) {
            List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            log.warn("Veri doğrulama hatası: {}", errorMessages);
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Gönderilen bilgiler eksik veya hatalı.");
            problemDetail.setProperty("hatalar", errorMessages);
            return problemDetail;
    }

}
