package org.example.vs.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.vs.booking.business.validation.BookableDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class DatePeriodDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @BookableDate(message = "Past starting date is not bookable")
    @NotNull(message = "Starting date is not specified")
    private final LocalDate fromDate;

    @BookableDate(message = "End date in the past is not bookable")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "End date is not specified")
    private final LocalDate toDate;
}
