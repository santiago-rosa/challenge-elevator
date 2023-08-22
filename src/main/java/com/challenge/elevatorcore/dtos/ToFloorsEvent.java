package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ToFloorsEvent {

    private Integer accessKey;
    @NotNull(message = "Target floors must be provided")
    private List<Integer> toFloors;
    @NotNull(message = "Elevator type must be provided")
    private ElevatorType elevatorType;

}
