package org.example.vs.booking.controller.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.vs.booking.dto.VehicleDto;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class AvailableForHireResponse {
    private final List<VehicleDto> vehicles;
}
