package org.example.vs.booking.controller.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.example.vs.booking.business.validation.ValidPeriod;
import org.example.vs.booking.dto.DatePeriodDto;

import java.util.UUID;

@Builder
@Getter
public class CreateBookingRequest {
    @NotNull
    private final UUID customerUuid;

    @NotNull
    private final UUID vehicleUuid;

    @NotNull
    @ValidPeriod
    private final DatePeriodDto datePeriod;
}
