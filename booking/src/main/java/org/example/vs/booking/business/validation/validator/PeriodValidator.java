package org.example.vs.booking.business.validation.validator;

import org.example.vs.booking.business.validation.ValidPeriod;
import org.example.vs.booking.dto.DatePeriodDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodValidator implements ConstraintValidator<ValidPeriod, DatePeriodDto> {
    @Override
    public boolean isValid(DatePeriodDto period, ConstraintValidatorContext context) {
        return period.getToDate().isEqual(period.getFromDate()) ||
                period.getToDate().isAfter(period.getFromDate());
    }
}
