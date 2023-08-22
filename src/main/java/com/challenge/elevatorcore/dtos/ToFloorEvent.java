package com.challenge.elevatorcore.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ToFloorEvent {

    private Integer accessKey;
    private List<Integer> toFloors;
    private ElevatorType elevatorType;

}
