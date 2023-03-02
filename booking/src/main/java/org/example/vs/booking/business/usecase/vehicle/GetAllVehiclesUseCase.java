package org.example.vs.booking.business.usecase.vehicle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vs.booking.business.UseCase;
import org.example.vs.booking.domain.entity.Vehicle;
import org.example.vs.booking.integration.database.repository.VehicleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class GetAllVehiclesUseCase {
    private final VehicleRepository vehicleRepository;

    @Transactional(readOnly = true)
    public List<Vehicle> exec() {
        log.info("Getting all vehicles");
        return vehicleRepository.findAll();
    }
}
