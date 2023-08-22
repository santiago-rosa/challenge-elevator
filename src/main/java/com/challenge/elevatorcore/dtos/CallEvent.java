package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CallEvent {

    @NotNull(message = "Elevator type must be provided")
    private ElevatorType elevatorType;
    @NotNull(message = "Calling floor must be provided")
    private Integer fromFloor;

}



