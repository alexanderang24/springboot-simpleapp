package com.example.demo.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourierValidator implements ConstraintValidator<CourierConstraint, String> {

    @Override
    public boolean isValid(String courier, ConstraintValidatorContext constraintValidatorContext) {
        if (courier == null) return false;
        return courier.equals("jne") || courier.equals("tiki") || courier.equals("pos");
    }
}
