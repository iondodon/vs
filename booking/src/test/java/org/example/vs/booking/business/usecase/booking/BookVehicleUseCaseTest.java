package org.example.vs.booking.business.usecase.booking;

import org.example.vs.booking.business.usecase.bookingdate.GetBookingDatesUseCase;
import org.example.vs.booking.business.usecase.vehicle.IsAvailableForHireUseCase;
import org.example.vs.booking.domain.entity.Customer;
import org.example.vs.booking.dto.DatePeriodDto;
import org.example.vs.booking.exception.AlreadyHiredException;
import org.example.vs.booking.exception.ResourceNotFoundException;
import org.example.vs.booking.integration.database.repository.CustomerRepository;
import org.example.vs.booking.domain.entity.BookingDate;
import org.example.vs.booking.domain.entity.Vehicle;
import org.example.vs.booking.integration.database.repository.BookingRepository;
import org.example.vs.booking.integration.database.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookVehicleUseCaseTest {
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private IsAvailableForHireUseCase isAvailableForHire;
    @Mock
    private GetBookingDatesUseCase getBookingDates;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private BookVehicleUseCase bookVehicleUseCase;

    @Test
    void testResourceNotFoundExceptionIsThrown_whenVehicleNotFound() {
        UUID vehicleUuid = randomUUID();
        DatePeriodDto period = new DatePeriodDto(LocalDate.now(), LocalDate.now());

        when(vehicleRepository.findByUuid(eq(vehicleUuid)))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> bookVehicleUseCase.execForPeriod(randomUUID(), vehicleUuid, period))
                .withMessage(format("Vehicle with UUID %s not found", vehicleUuid));
    }

    @Test
    void testExceptionIsThrown_whenCustomerNotFound() {
        UUID customerUuid = randomUUID();
        Vehicle vehicle = new Vehicle();
        vehicle.setUuid(randomUUID());
        DatePeriodDto period = new DatePeriodDto(LocalDate.now(), LocalDate.now());

        when(vehicleRepository.findByUuid(any())).thenReturn(Optional.of(vehicle));
        when(customerRepository.findByUuid(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> bookVehicleUseCase.execForPeriod(customerUuid, vehicle.getUuid(), period))
                .withMessage(format("Customer with UUID %s not found", customerUuid));
    }

    @Test
    void testThrowsAlreadyHiredException() {
        Customer customer = new Customer();
        customer.setUuid(randomUUID());
        Vehicle vehicle = new Vehicle();
        vehicle.setUuid(randomUUID());

        DatePeriodDto period = new DatePeriodDto(LocalDate.now(), LocalDate.now());

        when(vehicleRepository.findByUuid(any())).thenReturn(Optional.of(vehicle));
        when(customerRepository.findByUuid(any())).thenReturn(Optional.of(customer));
        when(isAvailableForHire.checkForPeriod(any(), any())).thenReturn(false);

        assertThatExceptionOfType(AlreadyHiredException.class)
                .isThrownBy(() -> bookVehicleUseCase.execForPeriod(customer.getUuid(), vehicle.getUuid(), period))
                .withMessage(format("Vehicle with UUID %s is already taken for at leas one day of this period", vehicle.getUuid()));
    }

    @Test
    void testBookingIsSaved() {
        Customer customer = new Customer();
        customer.setUuid(randomUUID());
        Vehicle vehicle = new Vehicle();
        vehicle.setUuid(randomUUID());

        DatePeriodDto period = new DatePeriodDto(LocalDate.now(), LocalDate.now());

        when(vehicleRepository.findByUuid(any())).thenReturn(Optional.of(vehicle));
        when(customerRepository.findByUuid(any())).thenReturn(Optional.of(customer));
        when(isAvailableForHire.checkForPeriod(any(), any())).thenReturn(true);

        HashSet<BookingDate> bookingDates = new HashSet<>();
        bookingDates.add(new BookingDate());
        when(getBookingDates.getForPeriod(any(), any()))
                .thenReturn(bookingDates);

        bookVehicleUseCase.execForPeriod(customer.getUuid(), vehicle.getUuid(), period);

        verify(bookingRepository, times(1)).save(any());
    }
}