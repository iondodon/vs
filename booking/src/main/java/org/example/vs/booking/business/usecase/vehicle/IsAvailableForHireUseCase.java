package org.example.vs.booking.business.usecase.vehicle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vs.booking.business.UseCase;
import org.example.vs.booking.dto.DatePeriodDto;
import org.example.vs.booking.integration.database.repository.VehicleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class IsAvailableForHireUseCase {
    private final VehicleRepository vehicleRepository;

    @Transactional(readOnly = true)
    public boolean checkForPeriod(UUID vehicleUuid, DatePeriodDto period) {
        return vehicleRepository
                .findBookedDatesForVehicleByPeriod(vehicleUuid, period.getFromDate(), period.getToDate())
                .isEmpty();
    }
}
