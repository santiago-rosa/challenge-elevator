package com.challenge.elevatorcore.gateways.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InMemoryEventSourceQueue implements ElevatorEventSourceGateway {

    private final ConcurrentLinkedQueue<Integer> eventQueue;

    public InMemoryEventSourceQueue(ConcurrentLinkedQueue<Integer> eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public void pushEvents(List<Integer> events) {
        eventQueue.addAll(events);
    }

    @Override
    public List<Integer> fetchAllEvents() {
        List<Integer> allEvents = new ArrayList<>();
        while (!eventQueue.isEmpty()) {
            allEvents.add(eventQueue.poll());
        }
        return allEvents;
    }

}
