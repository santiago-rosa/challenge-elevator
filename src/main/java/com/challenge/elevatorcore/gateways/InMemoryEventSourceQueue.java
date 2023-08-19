package com.challenge.elevatorcore.gateways;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@Scope("prototype")
public class InMemoryEventSourceQueue implements ElevatorEventSource {

    private final ConcurrentLinkedQueue<ElevatorEvent> eventQueue = new ConcurrentLinkedQueue<>();

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
