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

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/elevator")
public class ElevatorController {

    private final ElevatorService elevatorService;
    private final ElevatorEventMapper eventMapper;

    @Autowired
    public ElevatorController(ElevatorService elevatorService, ElevatorEventMapper eventMapper) {
        this.elevatorService = elevatorService;
        this.eventMapper = eventMapper;
    }

    @PostMapping("/calls")
    public ResponseEntity<String> processActions(@RequestBody @Valid List<CallElevatorAction> actions) {
        return getResponse(eventMapper.mapCallEventList(actions));
    }

    @PostMapping("/select_floors")
    public ResponseEntity<String> processSelectFloors(@RequestBody @Valid SelectFloorsAction action) {
        return getResponse(Collections.singletonList(eventMapper.mapSelectFloor(action)));
    }

    @PostMapping("/weight")
    public ResponseEntity<Void> updateMeasures(@RequestBody @Valid ElevatorWeightUpdate action) {
        elevatorService.updateWeight(eventMapper.mapToWeightEvent(action));
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> getResponse(List<ElevatorEvent> events) {
        try {
            elevatorService.receiveEvents(events);
        } catch (ElevatorServiceException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}

