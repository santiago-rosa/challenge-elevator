package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CallElevatorAction {

    @NotNull(message = "Elevator type must be provided")
    public String elevatorType;
    @NotNull(message = "Calling floor must be provided")
    public Integer fromFloor;

}

