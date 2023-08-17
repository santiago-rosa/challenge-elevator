package com.challenge.elevatorcore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/elevator")
public class ElevatorController {

    @PostMapping("/actions")
    ResponseEntity<Map<String, Object>> newEmployee(@RequestBody Map<String, Object> request) {
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

}
