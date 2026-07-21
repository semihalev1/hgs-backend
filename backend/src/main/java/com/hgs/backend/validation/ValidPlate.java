package com.hgs.backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPlate {
    String message() default "Geçersiz plaka formatı";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
