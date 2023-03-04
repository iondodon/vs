package org.example.vs.booking.presenter;

import org.assertj.core.api.Assertions;
import org.example.vs.booking.business.usecase.vehicle.GetAllVehiclesUseCase;
import org.example.vs.booking.business.usecase.vehicle.GetAvailableForHireUseCase;
import org.example.vs.booking.business.usecase.vehicle.GetCostOfHiringUseCase;
import org.example.vs.booking.controller.response.AllVehiclesResponse;
import org.example.vs.booking.controller.response.AvailableForHireResponse;
import org.example.vs.booking.domain.entity.Vehicle;
import org.example.vs.booking.exception.VbsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class VehiclePresenterTest {
    @Mock
    private GetAllVehiclesUseCase getAllVehiclesUseCase;
    @Mock
    private GetAvailableForHireUseCase getAvailableForHireUseCase;
    @Mock
    private GetCostOfHiringUseCase getCostOfHiringUseCase;
    @InjectMocks
    private VehiclePresenter vehiclePresenter;

    @Test
    void testReturnsCorrectVehicles() {
        Vehicle vehicle = new Vehicle();
        vehicle.setUuid(randomUUID());
        List<Vehicle> vehicles = singletonList(vehicle);
        when(getAllVehiclesUseCase.exec()).thenReturn(vehicles);

        AllVehiclesResponse allVehicles = vehiclePresenter.getAllVehicles();

        Assertions.assertThat(allVehicles.getVehicles()).hasSize(1);
        Assertions.assertThat(allVehicles.getVehicles().get(0).getUuid()).isEqualTo(vehicle.getUuid());
    }

    @Test
    void testGetAvailableForHire_returnsCorrectVehicle() {
        LocalDate currentDate = LocalDate.now();
        Vehicle vehicle = new Vehicle();
        vehicle.setUuid(UUID.randomUUID());

        when(getAvailableForHireUseCase.getByDate(any())).thenReturn(singletonList(vehicle));

        AvailableForHireResponse availableVehicles = vehiclePresenter.getAvailableForHireByDate(currentDate);

        Assertions.assertThat(availableVehicles.getVehicles()).hasSize(1);
        Assertions.assertThat(availableVehicles.getVehicles().get(0).getUuid()).isEqualTo(vehicle.getUuid());
    }

    @Test
    void testGetCostOfHiringThrows_whenInvalidPeriod() {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now();

        assertThatExceptionOfType(VbsException.class).isThrownBy(() -> vehiclePresenter.getCostByPeriod(randomUUID(), from, to)).withMessage("Starting date cannot be after end date");
    }

    @Test
    void testWasMultipliedToTheNumberOfDays() {
        UUID vehicleUuid = randomUUID();
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now();

        vehiclePresenter.getCostByPeriod(vehicleUuid, from, to);

        verify(getCostOfHiringUseCase, times(1)).byPeriod(eq(vehicleUuid), any());
    }
}