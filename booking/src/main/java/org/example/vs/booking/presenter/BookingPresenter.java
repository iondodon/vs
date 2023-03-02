package org.example.vs.booking.presenter;

import lombok.RequiredArgsConstructor;
import org.example.vs.booking.business.usecase.booking.BookVehicleUseCase;
import org.example.vs.booking.controller.request.CreateBookingRequest;

@Presenter
@RequiredArgsConstructor
public class BookingPresenter {
    private final BookVehicleUseCase bookVehicle;

    public void bookVehicle(CreateBookingRequest request) {
        bookVehicle.execForPeriod(request.getCustomerUuid(),
                request.getVehicleUuid(), request.getDatePeriod());
    }
}
