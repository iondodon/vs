package org.example.vs.booking.business.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.vs.booking.business.validation.validator.PeriodValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE})
@Constraint(validatedBy = PeriodValidator.class)
public @interface ValidPeriod {
    String message() default "Invalid period. toDate should be greater or equal to fromDate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}