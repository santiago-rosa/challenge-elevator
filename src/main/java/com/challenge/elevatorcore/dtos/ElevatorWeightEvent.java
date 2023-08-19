package com.challenge.elevatorcore.dtos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ElevatorWeightEvent {

    private final ElevatorType elevatorType;
    private final BigDecimal weight;

}
