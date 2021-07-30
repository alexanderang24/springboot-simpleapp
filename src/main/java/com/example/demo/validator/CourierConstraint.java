package com.example.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CourierValidator.class)
@Target({ ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CourierConstraint {

    String message() default "Invalid courier. Valid courier is: jne, tiki, pos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
