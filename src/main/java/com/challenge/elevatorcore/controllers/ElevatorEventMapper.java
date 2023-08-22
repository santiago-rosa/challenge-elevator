package com.challenge.elevatorcore.controllers;

import com.challenge.elevatorcore.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElevatorEventMapper {

    public List<CallEvent> mapCallEventList(List<CallElevatorAction> actionList) {
        return actionList.stream().map(action -> CallEvent.builder()
                .elevatorType(ElevatorType.valueOf(action.getElevatorType()))
                .fromFloor(action.getFromFloor())
                .build()).toList();
    }

    public ToFloorEvent mapSelectFloor(SelectFloorsAction action) {
        return ToFloorEvent.builder()
                .elevatorType(ElevatorType.valueOf(action.getElevatorType()))
                .toFloors(action.getToFloors())
                .accessKey(action.getAccessKey())
                .build();
    }

    public  ElevatorWeightEvent mapToWeightEvent(ElevatorWeightUpdate update) {
        return ElevatorWeightEvent.builder()
                .weight(update.getMeasure())
                .elevatorType(ElevatorType.valueOf(update.getElevatorType()))
                .build();
    }

}


