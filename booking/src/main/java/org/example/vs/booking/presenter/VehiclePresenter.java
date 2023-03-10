package org.example.vs.booking.presenter;

import lombok.RequiredArgsConstructor;
import org.example.vs.booking.business.usecase.vehicle.GetAllVehiclesUseCase;
import org.example.vs.booking.business.usecase.vehicle.GetAvailableForHireUseCase;
import org.example.vs.booking.controller.response.AllVehiclesResponse;
import org.example.vs.booking.controller.response.AvailableForHireResponse;
import org.example.vs.booking.business.usecase.vehicle.GetCostOfHiringUseCase;
import org.example.vs.booking.controller.response.CostResponse;
import org.example.vs.booking.dto.VehicleDto;
import org.example.vs.booking.exception.VbsException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Presenter
@RequiredArgsConstructor
public class VehiclePresenter {
    private static final String INVALID_PERIOD = "Starting date cannot be after end date";
    private final GetAllVehiclesUseCase getAllVehiclesUseCase;
    private final GetAvailableForHireUseCase getAvailableForHireUseCase;
    private final GetCostOfHiringUseCase getCostOfHiringUseCase;

    public AllVehiclesResponse getAllVehicles() {
        List<VehicleDto> vehicleDtos = getAllVehiclesUseCase.exec()
                .stream()
                .map(VehicleDto::from)
                .collect(toList());
        return new AllVehiclesResponse(vehicleDtos);
    }

    public AvailableForHireResponse getAvailableForHireByDate(LocalDate date) {
        List<VehicleDto> availableVehicles = getAvailableForHireUseCase.getByDate(date)
                .stream()
                .map(VehicleDto::from)
                .collect(toList());
        return new AvailableForHireResponse(availableVehicles);
    }

    public CostResponse getCostByPeriod(UUID vehicleUuid, LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)) {
            throw new VbsException(INVALID_PERIOD);
        }
        Period periodInclusive = Period.between(fromDate, toDate.plusDays(1));
        BigDecimal cost = getCostOfHiringUseCase.byPeriod(vehicleUuid, periodInclusive);
        return new CostResponse(cost);
    }
}
