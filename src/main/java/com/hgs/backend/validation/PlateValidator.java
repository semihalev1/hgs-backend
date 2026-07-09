package com.hgs.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlateValidator implements ConstraintValidator<ValidPlate, String> {
    private static final String PLATE_REGEX = "^(0[1-9]|[1-7][0-9]|8[0-1])[A-Z]{1,3}[0-9]{2,4}$";

    @Override
    public void initialize (ValidPlate constraintAnnotation) {}

    @Override
    public boolean isValid(String plate, ConstraintValidatorContext context) {
        if(plate == null || plate.trim().isEmpty()) {
            return true;
        }

        return plate.matches(PLATE_REGEX);
    }
}
