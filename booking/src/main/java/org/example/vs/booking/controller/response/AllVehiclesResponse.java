package org.example.vs.booking.controller.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.vs.booking.dto.VehicleDto;

import java.util.List;


@Data
@RequiredArgsConstructor
public class AllVehiclesResponse {
    private final List<VehicleDto> vehicles;
}
