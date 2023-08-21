package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ElevatorWeightUpdate {

    @NotNull
    public BigDecimal measure;
    @NotNull
    public String elevatorType;

}
