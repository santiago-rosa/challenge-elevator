package com.challenge.elevatorcore.gateways.events;

import com.challenge.elevatorcore.dtos.CallEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
class InMemoryEventSourceQueueTest {

    @Mock
    ConcurrentLinkedQueue<Integer> eventQueue;

    private ElevatorEventSourceGateway userGateway;

    @BeforeEach
    void setUp() {
        userGateway = new InMemoryEventSourceQueue(eventQueue);
    }

    @Test
    public void pushEvents() {
        //Given
        List<Integer> events = Collections.singletonList(1);

        //When
        userGateway.pushEvents(events);

        //Then
        verify(eventQueue, times(1)).addAll(events);
    }

    @Test
    public void fetchAllEvents() {
        //Given
        when(eventQueue.isEmpty()).thenReturn(false, false, false, true);
        when(eventQueue.poll()).thenReturn(1, 2, 3);

        //When
        List<Integer> events = userGateway.fetchAllEvents();

        //Then
        verify(eventQueue, times(3)).poll();
        assertEquals(events.size(), 3);

    }
}
