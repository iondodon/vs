package org.example.vs.booking.controller;

import org.example.vs.booking.controller.response.AllVehiclesResponse;
import org.example.vs.booking.business.validation.BookableDate;
import org.example.vs.booking.controller.response.AvailableForHireResponse;
import org.example.vs.booking.controller.response.CostResponse;
import org.example.vs.booking.presenter.VehiclePresenter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;


@Validated
@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehiclePresenter vehiclePresenter;

    @GetMapping
    @Operation(summary = "Get all the vehicles the company has")
    public AllVehiclesResponse getAllVehicles() {
        return vehiclePresenter.getAllVehicles();
    }

    @GetMapping(value = "/available")
    @Operation(summary = "Get available vehicles for hire on a given date")
    public AvailableForHireResponse getAvailableByDate(
            @RequestParam
            @NotNull(message = "Missing date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @BookableDate LocalDate date
    ) {
        return vehiclePresenter.getAvailableForHireByDate(date);
    }

    @GetMapping("/{vehicleUuid}/cost")
    @Operation(summary = "Get cost of hiring a vehicle by date range")
    public CostResponse getCostByPeriod(
            @PathVariable("vehicleUuid") UUID vehicleUuid,

            @NotNull(message = "Missing starting date")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,

            @NotNull(message = "Missing end date")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        return vehiclePresenter.getCostByPeriod(vehicleUuid, fromDate, toDate);
    }
}
