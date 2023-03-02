package org.example.vs.booking.business.usecase.vehicle;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vs.booking.business.UseCase;
import org.example.vs.booking.domain.entity.Vehicle;
import org.example.vs.booking.integration.database.repository.VehicleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class GetAvailableForHireUseCase {
    private final VehicleRepository vehicleRepository;

    @Transactional(readOnly = true)
    public List<Vehicle> getByDate(LocalDate date) {
        log.info("Get all available vehicles on date {}", date);
        return vehicleRepository.getAvailableForHireOnDate(date);
    }
}
