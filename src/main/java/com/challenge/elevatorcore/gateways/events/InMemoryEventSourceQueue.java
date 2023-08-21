package com.challenge.elevatorcore.gateways.events;

import com.challenge.elevatorcore.dtos.ElevatorEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InMemoryEventSourceQueue implements ElevatorEventSourceGateway {

    private final ConcurrentLinkedQueue<ElevatorEvent> eventQueue;

    public InMemoryEventSourceQueue(ConcurrentLinkedQueue<ElevatorEvent> eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public void pushEvents(List<ElevatorEvent> events) {
        eventQueue.addAll(events);
    }

    @Override
    public List<ElevatorEvent> fetchAllEvents() {
        List<ElevatorEvent> allEvents = new ArrayList<>();
        while (!eventQueue.isEmpty()) {
            allEvents.add(eventQueue.poll());
        }
        return allEvents;
    }

}
