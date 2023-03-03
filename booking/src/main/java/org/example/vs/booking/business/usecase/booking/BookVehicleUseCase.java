package org.example.vs.booking.business.usecase.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vs.booking.business.UseCase;
import org.example.vs.booking.business.usecase.bookingdate.GetBookingDatesUseCase;
import org.example.vs.booking.business.usecase.vehicle.IsAvailableForHireUseCase;
import org.example.vs.booking.domain.entity.Booking;
import org.example.vs.booking.domain.entity.BookingDate;
import org.example.vs.booking.domain.entity.Customer;
import org.example.vs.booking.domain.entity.Vehicle;
import org.example.vs.booking.dto.DatePeriodDto;
import org.example.vs.booking.exception.AlreadyHiredException;
import org.example.vs.booking.exception.ResourceNotFoundException;
import org.example.vs.booking.integration.database.repository.BookingRepository;
import org.example.vs.booking.integration.database.repository.CustomerRepository;
import org.example.vs.booking.integration.database.repository.VehicleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.UUID.randomUUID;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class BookVehicleUseCase {
    private static final String VEHICLE_NOT_FOUND = "Vehicle with UUID %s not found";
    private static final String ALREADY_HIRED =
            "Vehicle with UUID %s is already taken for at leas one day of this period";
    private static final String CUSTOMER_NOT_FOUND = "Customer with UUID %s not found";

    private final VehicleRepository vehicleRepository;
    private final IsAvailableForHireUseCase isAvailableForHire;
    private final GetBookingDatesUseCase getBookingDates;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;


    @Transactional
    public void execForPeriod(UUID customerUuid, UUID vehicleUuid, DatePeriodDto period) {
        LocalDate startDate = period.getFromDate();
        LocalDate endDate = period.getToDate();
        log.info("Booking vehicle with UUID {} starting from {} to {}", vehicleUuid, startDate, endDate);

        Vehicle vehicle = vehicleRepository.findByUuid(vehicleUuid)
                .orElseThrow(() -> new ResourceNotFoundException(format(VEHICLE_NOT_FOUND, vehicleUuid)));
        Customer customer = customerRepository.findByUuid(customerUuid)
                .orElseThrow(() -> new ResourceNotFoundException(format(CUSTOMER_NOT_FOUND, customerUuid)));

        if (!isAvailableForHire.checkForPeriod(vehicleUuid, period)) {
            throw new AlreadyHiredException(format(ALREADY_HIRED, vehicleUuid));
        }

        log.info("Booking vehicle with UUID {}", vehicleUuid);

        Set<BookingDate> datesToBook = getBookingDates.getForPeriod(startDate, endDate);
        Booking newBooking = Booking.builder()
                .uuid(randomUUID())
                .vehicle(vehicle)
                .bookedDates(datesToBook)
                .customer(customer)
                .build();

        bookingRepository.save(newBooking);
    }
}
