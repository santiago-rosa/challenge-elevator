package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorEventType;
import com.challenge.elevatorcore.dtos.ElevatorLock;
import com.challenge.elevatorcore.dtos.ElevatorType;
import com.challenge.elevatorcore.entities.keyaccess.KeyAccessFilter;
import com.challenge.elevatorcore.entities.validation.WeightLimitChecker;
import com.challenge.elevatorcore.gateways.events.ElevatorEventSourceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PublicElevatorTest {

    @Mock
    private WeightLimitChecker weightLimitChecker;

    @Mock
    private KeyAccessFilter keyAccessFilter;

    @Mock
    private ElevatorEventSourceGateway elevatorEventSource;

    private PublicElevator elevator;

    @BeforeEach
    void setUp() {
        elevator = new PublicElevator(weightLimitChecker, keyAccessFilter, elevatorEventSource);
    }

    @Test
    void testProcessEvents() {
        List<ElevatorEvent> events = List.of(mock(ElevatorEvent.class));
        List<ElevatorEvent> filteredEvents = List.of(mock(ElevatorEvent.class));

        when(keyAccessFilter.filter(events)).thenReturn(filteredEvents);

        elevator.processEvents(events);

        verify(keyAccessFilter).filter(events);
        verify(elevatorEventSource).pushEvents(filteredEvents);
    }

    @Test
    void testMatches() {
        assertTrue(elevator.matches(ElevatorType.PUBLIC));
        assertFalse(elevator.matches(ElevatorType.FREIGHT));
    }

    @Test
    void testUpdateWeight() {
        //When
        elevator.updateWeight(new BigDecimal("150.0"));
        assertEquals(new BigDecimal("150.0"), elevator.getCurrentWeight());
    }

    @Test
    void testOverweightScheduled() {
        // Given
        BigDecimal overweightValue = new BigDecimal("1500.0");
        elevator.updateWeight(overweightValue);
        when(weightLimitChecker.overweightLock(overweightValue)).thenReturn(new ElevatorLock(true, "Overweight"));

        // When
        elevator.scheduledMove();

        //Then
        assertEquals("Overweight", elevator.getLock().reason);
        assertEquals(0, elevator.getCurrentPosition());
    }

    @Test
    void testNoOverweightScheduled() {
        // Given
        BigDecimal normalWeightValue = new BigDecimal("50.0");
        elevator.updateWeight(normalWeightValue);

        when(weightLimitChecker.overweightLock(normalWeightValue)).thenReturn(new ElevatorLock(false, ""));

        // When
        elevator.scheduledMove();

        // Then
        assertFalse(elevator.getLock().active);
    }

    @Test
    void testMoveWhenPathIsEmpty() {
        // Given
        when(weightLimitChecker.overweightLock(any())).thenReturn(new ElevatorLock(false, ""));
        when(elevatorEventSource.fetchAllEvents()).thenReturn(Collections.emptyList());

        int initialPosition = elevator.getCurrentPosition();

        // When
        elevator.scheduledMove();

        // Then
        assertEquals(initialPosition, elevator.getCurrentPosition());
    }

    @Test
    void testMoveToTargetFloor() {
        // Given
        List<ElevatorEvent> mockEvents = List.of(ElevatorEvent.builder()
                .eventType(ElevatorEventType.CALL_ELEVATOR)
                .elevatorType(ElevatorType.PUBLIC)
                .fromFloor(5)
                .build());
        when(elevatorEventSource.fetchAllEvents()).thenReturn(mockEvents);
        when(weightLimitChecker.overweightLock(any())).thenReturn(new ElevatorLock(false, ""));

        // When
        elevator.scheduledMove();

        // Then
        assertEquals(5, elevator.getCurrentPosition());
    }


}
