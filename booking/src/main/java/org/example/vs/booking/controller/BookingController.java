package org.example.vs.booking.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.vs.booking.controller.request.CreateBookingRequest;
import org.example.vs.booking.presenter.BookingPresenter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingPresenter bookingPresenter;

    @Operation(summary = "Create new booking")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBooking(@RequestBody @Valid CreateBookingRequest request) {
        bookingPresenter.bookVehicle(request);
    }
}
