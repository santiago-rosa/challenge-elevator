package com.challenge.elevatorcore.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CallEvent {

    private ElevatorType elevatorType;
    private Integer fromFloor;

}



