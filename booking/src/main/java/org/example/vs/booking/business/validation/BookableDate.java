package org.example.vs.booking.business.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.FutureOrPresent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@FutureOrPresent(message = "Past date is not bookable")
@Constraint(validatedBy = {})
public @interface BookableDate {
    String message() default "Past date is not bookable";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}