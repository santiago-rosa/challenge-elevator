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
        List<CallEvent> events = List.of(CallEvent.builder().fromFloor(5).build());

        elevator.processCalls(events);

        verify(elevatorEventSource).pushEvents(List.of(5));
    }

    @Test
    void testProcessToFloorAuthorized() {
        ToFloorsEvent event = ToFloorsEvent.builder().toFloors(List.of(1, 2, 3, 4)).build();
        when(keyAccessAuthorizer.authorized(event)).thenReturn(true);

        elevator.processToFloor(event);

        verify(elevatorEventSource).pushEvents(List.of(1, 2, 3, 4));
    }

    @Test
    void testMatches() {
        assertTrue(elevator.matches(ElevatorType.PUBLIC));
        assertFalse(elevator.matches(ElevatorType.FREIGHT));
    }

    @Test
    void testUpdateWeight() {
        elevator.updateWeight(new BigDecimal("150.0"));
        assertEquals(new BigDecimal("150.0"), elevator.getCurrentWeight());
    }

    @Test
    void testOverweightScheduled() {
        BigDecimal overweightValue = new BigDecimal("1500.0");
        elevator.updateWeight(overweightValue);
        when(weightLimitChecker.overweightLock(overweightValue)).thenReturn(new ElevatorLock(true, "Overweight"));

        elevator.scheduledMove();

        assertEquals("Overweight", elevator.getLock().reason);
        assertEquals(0, elevator.getCurrentPosition());
    }

    @Test
    void testNoOverweightScheduled() {
        BigDecimal normalWeightValue = new BigDecimal("50.0");
        elevator.updateWeight(normalWeightValue);

        when(weightLimitChecker.overweightLock(normalWeightValue)).thenReturn(new ElevatorLock(false, ""));

        elevator.scheduledMove();

        assertFalse(elevator.getLock().active);
    }

    @Test
    void testMoveWhenPathIsEmpty() {
        when(weightLimitChecker.overweightLock(any())).thenReturn(new ElevatorLock(false, ""));
        when(elevatorEventSource.fetchAllEvents()).thenReturn(Collections.emptyList());

        int initialPosition = elevator.getCurrentPosition();

        elevator.scheduledMove();

        assertEquals(initialPosition, elevator.getCurrentPosition());
    }

    @Test
    void testMoveToTargetFloor() {
        when(elevatorEventSource.fetchAllEvents()).thenReturn(List.of(5));
        when(weightLimitChecker.overweightLock(any())).thenReturn(new ElevatorLock(false, ""));

        elevator.scheduledMove();

        assertEquals(5, elevator.getCurrentPosition());
    }


}
