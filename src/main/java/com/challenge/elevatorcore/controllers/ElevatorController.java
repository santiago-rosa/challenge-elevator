package com.challenge.elevatorcore.controllers;

import com.challenge.elevatorcore.dtos.*;
import com.challenge.elevatorcore.services.ElevatorService;
import com.challenge.elevatorcore.services.exceptions.ElevatorServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        try {
            elevatorService.receiveCalls(eventMapper.mapCallEventList(actions));
        } catch (ElevatorServiceException e) {
            return badRequest(e);
        }
        return ok();
    }

    @PostMapping("/select_floors")
    public ResponseEntity<String> processSelectFloors(@RequestBody @Valid SelectFloorsAction action) {
        try {
            elevatorService.goToFloors(eventMapper.mapSelectFloor(action));
        } catch (ElevatorServiceException e) {
            return badRequest(e);
        }
        return ok();
    }

    @PostMapping("/weight")
    public ResponseEntity<String> updateMeasures(@RequestBody @Valid WeightChangeAction action) {
        elevatorService.updateWeight(eventMapper.mapToWeightEvent(action));
        return ok();
    }

    private static ResponseEntity<String> ok() {
        return ResponseEntity.ok().build();
    }

    private static ResponseEntity<String> badRequest(ElevatorServiceException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}

