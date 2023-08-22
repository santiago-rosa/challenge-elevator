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

@RestController
@RequestMapping("/api/elevator")
public class ElevatorController {

    private final ElevatorService elevatorService;

    @Autowired
    public ElevatorController(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @PostMapping("/calls")
    public ResponseEntity<String> processActions(@RequestBody @Valid List<CallEvent> events) {
        try {
            elevatorService.receiveCalls(events);
        } catch (ElevatorServiceException e) {
            return badRequest(e);
        }
        return ok();
    }

    @PostMapping("/select_floors")
    public ResponseEntity<String> processSelectFloors(@RequestBody @Valid ToFloorsEvent event) {
        try {
            elevatorService.goToFloors(event);
        } catch (ElevatorServiceException e) {
            return badRequest(e);
        }
        return ok();
    }

    @PostMapping("/weight")
    public ResponseEntity<String> updateMeasures(@RequestBody @Valid ElevatorWeightEvent event) {
        elevatorService.updateWeight(event);
        return ok();
    }

    private static ResponseEntity<String> ok() {
        return ResponseEntity.ok().build();
    }

    private static ResponseEntity<String> badRequest(ElevatorServiceException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}

