package com.challenge.elevatorcore.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Builder
public class ElevatorEvent {

    private ElevatorType elevatorType;
    private ElevatorEventType eventType;
    private Integer fromFloor;
    private Integer accessKey;
    private List<Integer> toFloors;

}



