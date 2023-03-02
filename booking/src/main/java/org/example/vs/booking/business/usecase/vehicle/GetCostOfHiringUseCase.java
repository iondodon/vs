package org.example.vs.booking.business.usecase.vehicle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vs.booking.business.UseCase;
import org.example.vs.booking.exception.ResourceNotFoundException;
import org.example.vs.booking.integration.database.repository.VehicleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Period;
import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class GetCostOfHiringUseCase {
    private static final String VEHICLE_NOT_FOUND = "Vehicle with UUID %s not found";
    private final VehicleRepository vehicleRepository;

    @Transactional(readOnly = true)
    public BigDecimal byPeriod(UUID vehicleUuid, Period period) {
        log.info("Get price to hire vehicle with UUID {} for period {}", vehicleUuid, period);
        return vehicleRepository.findByUuid(vehicleUuid)
                .orElseThrow(() -> new ResourceNotFoundException(format(VEHICLE_NOT_FOUND, vehicleUuid)))
                .getCategory()
                .getPricePerDay()
                .multiply(new BigDecimal(period.getDays()));
    }
}
