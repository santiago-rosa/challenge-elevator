package com.challenge.elevatorcore.gateways.events;

import com.challenge.elevatorcore.dtos.ElevatorEvent;

import java.util.List;

public interface ElevatorEventSourceGateway {

    void pushEvents(List<ElevatorEvent> events);

    List<ElevatorEvent> fetchAllEvents();

}
