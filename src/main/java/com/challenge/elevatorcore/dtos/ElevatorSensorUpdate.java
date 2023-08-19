package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ElevatorSensorUpdate {

    @NotNull
    public BigDecimal measure;
    @NotNull
    public String elevatorType;

}
