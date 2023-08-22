package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.*;
import com.challenge.elevatorcore.entities.keyaccess.KeyAccessAuthorizer;
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
    private KeyAccessAuthorizer keyAccessAuthorizer;

    @Mock
    private ElevatorEventSourceGateway elevatorEventSource;

    private PublicElevator elevator;

    @BeforeEach
    void setUp() {
        elevator = new PublicElevator(weightLimitChecker, keyAccessAuthorizer, elevatorEventSource);
    }

    @Test
    void testProcessEvents() {
        //Given
        List<CallEvent> events = List.of(CallEvent.builder().fromFloor(5).build());
        List<CallEvent> filteredEvents = List.of(mock(CallEvent.class));

        //When
        elevator.processCalls(events);

        //Then
        verify(elevatorEventSource).pushEvents(List.of(5));
    }

    @Test
    void testProcessToFloorAuthorized() {
        //Given
        ToFloorEvent event = ToFloorEvent.builder().toFloors(List.of(1, 2, 3, 4)).build();
        when(keyAccessAuthorizer.authorized(event)).thenReturn(true);

        //When
        elevator.processToFloor(event);
        //Then
        verify(elevatorEventSource).pushEvents(List.of(1, 2, 3, 4));
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
        when(elevatorEventSource.fetchAllEvents()).thenReturn(List.of(5));
        when(weightLimitChecker.overweightLock(any())).thenReturn(new ElevatorLock(false, ""));

        // When
        elevator.scheduledMove();

        // Then
        assertEquals(5, elevator.getCurrentPosition());
    }


}
