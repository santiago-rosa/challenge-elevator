package com.challenge.elevatorcore.entities.validation;

import com.challenge.elevatorcore.dtos.ElevatorLock;

import java.math.BigDecimal;

public class WeightLimitChecker {

    private final BigDecimal maxWeight;

    public WeightLimitChecker(BigDecimal maxWeight) {
        this.maxWeight = maxWeight;
    }

    public ElevatorLock overweightLock(BigDecimal currentWeight) {
        if (currentWeight.compareTo(maxWeight) > 0) {
            return new ElevatorLock(true, "Overweight");
        }
        return new ElevatorLock(false, "");
    }

}
