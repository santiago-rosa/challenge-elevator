package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CallElevatorAction {

    @NotNull
    public String elevatorType;
    @NotNull
    public Integer fromFloor;

}

