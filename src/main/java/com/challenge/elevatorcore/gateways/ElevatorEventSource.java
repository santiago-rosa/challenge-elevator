package com.challenge.elevatorcore.gateways;

import com.challenge.elevatorcore.dtos.ElevatorEvent;

import java.util.List;

public interface ElevatorEventSource {

    void pushEvents(List<ElevatorEvent> events);

    List<ElevatorEvent> fetchAllEvents();

}
