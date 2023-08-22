package com.challenge.elevatorcore.gateways.events;

import java.util.List;

public interface ElevatorEventSourceGateway {

    void pushEvents(List<Integer> events);

    List<Integer> fetchAllEvents();

}
