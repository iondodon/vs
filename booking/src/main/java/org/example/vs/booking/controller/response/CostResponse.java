package org.example.vs.booking.controller.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class CostResponse {
    @NotNull
    private final BigDecimal cost;
}
