package org.example.vs.booking.dto;

import org.example.vs.booking.domain.entity.VehicleCategory;
import org.example.vs.booking.domain.type.VehicleType;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class VehicleCategoryDto {
    @NotNull
    private final VehicleType category;
    @NotNull
    private final BigDecimal pricePerDay;

    public static VehicleCategoryDto from(VehicleCategory category) {
        if (category == null) {
            return null;
        }
        return VehicleCategoryDto.builder()
            .category(category.getCategory())
            .pricePerDay(category.getPricePerDay())
            .build();
    }
}
