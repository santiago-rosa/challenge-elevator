package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ElevatorWeightEvent {

    @NotNull(message = "Measure must be provided")
    private final BigDecimal weight;
    @NotNull(message = "Elevator type must be provided")
    private final ElevatorType elevatorType;

}
