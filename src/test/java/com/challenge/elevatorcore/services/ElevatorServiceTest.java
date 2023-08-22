package com.challenge.elevatorcore.services;

import com.challenge.elevatorcore.dtos.CallEvent;
import com.challenge.elevatorcore.dtos.ElevatorType;
import com.challenge.elevatorcore.dtos.ElevatorWeightEvent;
import com.challenge.elevatorcore.dtos.ToFloorsEvent;
import com.challenge.elevatorcore.entities.elevator.Elevator;
import com.challenge.elevatorcore.services.validation.ElevatorEventValidator;
import com.challenge.elevatorcore.services.validation.ToFloorEventValidator;
import com.challenge.elevatorcore.services.validation.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class ElevatorServiceTest {

    @Mock
    private Elevator elevator;
    @Mock
    private ElevatorEventValidator callEventValidator;
    @Mock
    private ToFloorEventValidator toFloorEventValidator;

    private ElevatorService elevatorService;

    @BeforeEach
    void setUp() {
        when(callEventValidator.execute(any())).thenReturn(new ValidationResult("", false));
        when(toFloorEventValidator.execute(any())).thenReturn(new ValidationResult("", false));
        when(elevator.matches(any())).thenReturn(true);
        when(elevator.matches(any())).thenReturn(true);
        elevatorService = new ElevatorServiceImpl(
                Collections.singletonList(elevator),
                Collections.singletonList(callEventValidator),
                Collections.singletonList(toFloorEventValidator));
    }

    @Test
    void receiveCalls() {
        List<CallEvent> events = List.of(CallEvent.builder()
                .fromFloor(5)
                .elevatorType(ElevatorType.FREIGHT)
                .build());

        elevatorService.receiveCalls(events);

        verify(elevator, times(1)).processCalls(anyList());
        verify(elevator, times(1)).matches(ElevatorType.FREIGHT);

    }

    @Test
    void goToFloors() {
        ToFloorsEvent events = ToFloorsEvent.builder()
                .toFloors(List.of(5, 6))
                .accessKey(33)
                .elevatorType(ElevatorType.FREIGHT)
                .build();

        elevatorService.goToFloors(events);

        verify(elevator, times(1)).processToFloor(any(ToFloorsEvent.class));
        verify(elevator, times(1)).matches(ElevatorType.FREIGHT);
    }

    @Test
    void weight() {
        ElevatorWeightEvent event = ElevatorWeightEvent.builder()
                .elevatorType(ElevatorType.FREIGHT)
                .weight(new BigDecimal(600))
                .build();

        elevatorService.updateWeight(event);

        verify(elevator, times(1)).updateWeight(any(BigDecimal.class));
        verify(elevator, times(1)).matches(ElevatorType.FREIGHT);
    }

}
