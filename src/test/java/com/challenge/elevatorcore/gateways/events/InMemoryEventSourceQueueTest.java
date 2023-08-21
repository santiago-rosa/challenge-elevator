package com.challenge.elevatorcore.gateways.events;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.mockito.Mockito.*;


@SpringBootTest
class InMemoryEventSourceQueueTest {

    @Mock
    ConcurrentLinkedQueue<ElevatorEvent> eventQueue;

    @InjectMocks
    private ElevatorEventSourceGateway userGateway = new InMemoryEventSourceQueue(eventQueue);

    @Test
    public void pushEvents() {
        //Given
        List<ElevatorEvent> events = Collections.singletonList(ElevatorEvent.builder().build());

        //When
        userGateway.pushEvents(events);

        //Then
        verify(eventQueue, times(1)).addAll(events);
    }

    @Test
    public void fetchAllEvents() {
        //Given
        when(eventQueue.isEmpty()).thenReturn(false, false, false, true);
        when(eventQueue.poll()).thenReturn(ElevatorEvent.builder().build());

        //When
        List<ElevatorEvent> events = userGateway.fetchAllEvents();

        //Then
        verify(eventQueue, times(3)).addAll(events);

    }
}
