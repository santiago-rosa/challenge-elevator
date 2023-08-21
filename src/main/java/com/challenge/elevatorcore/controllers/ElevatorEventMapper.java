package com.challenge.elevatorcore.controllers;

import com.challenge.elevatorcore.dtos.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElevatorEventMapper {

    public List<ElevatorEvent> mapCallEventList(List<CallElevatorAction> actionList) {
        return actionList.stream().map(action -> ElevatorEvent.builder()
                .elevatorType(ElevatorType.valueOf(action.getElevatorType()))
                .eventType((ElevatorEventType.CALL_ELEVATOR))
                .fromFloor(action.getFromFloor())
                .build()).toList();
    }

    public ElevatorEvent mapSelectFloor(SelectFloorsAction action) {
        return ElevatorEvent.builder()
                .elevatorType(ElevatorType.valueOf(action.getElevatorType()))
                .eventType((ElevatorEventType.SELECT_FLOORS))
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


