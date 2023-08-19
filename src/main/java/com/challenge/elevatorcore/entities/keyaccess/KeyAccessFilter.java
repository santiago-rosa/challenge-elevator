package com.challenge.elevatorcore.entities.keyaccess;

import com.challenge.elevatorcore.dtos.ElevatorEvent;

import java.util.List;

public interface KeyAccessFilter {
    List<ElevatorEvent> filter(List<ElevatorEvent> events);
}
