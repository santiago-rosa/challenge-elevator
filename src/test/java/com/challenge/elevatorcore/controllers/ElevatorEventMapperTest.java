package com.challenge.elevatorcore.controllers;

import com.challenge.elevatorcore.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorEventMapperTest {

    private ElevatorEventMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ElevatorEventMapper();
    }

    @Test
    public void testMapCallEventList() {
        CallElevatorAction action = CallElevatorAction.builder()
                .elevatorType("PUBLIC")
                .fromFloor(5)
                .build();

        List<ElevatorEvent> events = mapper.mapCallEventList(Collections.singletonList(action));

        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals(ElevatorType.PUBLIC, events.get(0).getElevatorType());
        assertEquals(ElevatorEventType.CALL_ELEVATOR, events.get(0).getEventType());
        assertEquals(5, events.get(0).getFromFloor());
    }

    @Test
    public void testMapSelectFloor() {
        SelectFloorsAction action = SelectFloorsAction.builder()
                .elevatorType("FREIGHT")
                .toFloors(Arrays.asList(1, 2))
                .accessKey(1234)
                .build();


        ElevatorEvent event = mapper.mapSelectFloor(action);

        assertNotNull(event);
        assertEquals(ElevatorType.FREIGHT, event.getElevatorType());
        assertEquals(ElevatorEventType.SELECT_FLOORS, event.getEventType());
        assertEquals(Arrays.asList(1, 2), event.getToFloors());
        assertEquals(1234, event.getAccessKey());
    }
}

