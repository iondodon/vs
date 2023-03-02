package org.example.vs.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import org.example.vs.booking.domain.entity.Vehicle;
import org.example.vs.booking.domain.type.FuelType;

import java.util.UUID;


@Value
@Builder
public class VehicleDto {
    @NotNull
    private final UUID uuid;
    @NotBlank
    private final String registrationNumber;
    @NotBlank
    private final String make;
    @NotBlank
    private final String model;
    @NotNull
    private final FuelType fuelType;
    @NotNull
    private final VehicleCategoryDto vehicleCategory;


    public static VehicleDto from(Vehicle vehicle) {
        return VehicleDto.builder()
                .uuid(vehicle.getUuid())
                .registrationNumber(vehicle.getRegistrationNumber())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .fuelType(vehicle.getFuelType())
                .vehicleCategory(VehicleCategoryDto.from(vehicle.getCategory()))
                .build();
    }
}
