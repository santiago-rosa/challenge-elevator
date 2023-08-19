package com.challenge.elevatorcore.controllers;

import com.challenge.elevatorcore.dtos.*;
import com.challenge.elevatorcore.services.ElevatorService;
import com.challenge.elevatorcore.services.exceptions.ElevatorServiceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/elevator")
public class ElevatorController {

    private final ElevatorService elevatorService;

    @Autowired
    public ElevatorController(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @PostMapping("/actions")
    public ResponseEntity<String> processActions(@RequestBody @Valid List<ElevatorActionDTO> actions) {
        List<ElevatorEvent> events = actions.stream()
                .map(this::mapToEvent)
                .collect(Collectors.toList());
        try {
            elevatorService.receiveEvents(events);
        } catch (ElevatorServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/weight")
    public ResponseEntity<Void> updateMeasures(@RequestBody @Valid ElevatorSensorUpdate action) {
        elevatorService.updateWeight(mapToSensorEvent(action));
        return ResponseEntity.ok().build();
    }

    //TODO move to a mapper
    private ElevatorWeightEvent mapToSensorEvent(ElevatorSensorUpdate update) {
        return ElevatorWeightEvent.builder()
                .weight(update.getMeasure())
                .elevatorType(ElevatorType.valueOf(update.getElevatorType()))
                .build();
    }

    //TODO move to a mapper
    private ElevatorEvent mapToEvent(ElevatorActionDTO action) {
        return switch (action.getEventType()) {
            case "CALL_ELEVATOR" -> ElevatorEvent.builder()
                    .elevatorType(ElevatorType.valueOf(action.getElevatorType()))
                    .eventType(ElevatorEventType.valueOf(action.getEventType()))
                    .fromFloor(action.getFromFloor())
                    .accessKey(action.getAccessKey())
                    .build();
            case "SELECT_FLOORS" -> ElevatorEvent.builder()
                    .elevatorType(ElevatorType.valueOf(action.getElevatorType()))
                    .eventType(ElevatorEventType.valueOf(action.getEventType()))
                    .fromFloor(action.getFromFloor())
                    .toFloors(action.getToFloors())
                    .accessKey(action.getAccessKey())
                    .build();
            default -> throw new IllegalArgumentException("Unsupported event type: " + action.getEventType());
        };
    }
}

