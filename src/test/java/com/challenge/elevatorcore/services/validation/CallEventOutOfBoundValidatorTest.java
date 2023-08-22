package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.CallEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CallEventOutOfBoundValidatorTest {

    private CallEventOutOfBoundValidator callEventOutOfBoundValidator;

    @Mock
    private FloorOutOfBoundValidator floorOutOfBoundValidator;

    @BeforeEach
    public void setUp() {
        when(floorOutOfBoundValidator.validate(List.of(5, 6))).thenReturn(new ValidationResult("", false));
        callEventOutOfBoundValidator = new CallEventOutOfBoundValidator(floorOutOfBoundValidator);
    }

    @Test
    public void testExecuteWithValidFloors() {
        List<CallEvent> events = Arrays.asList(
                CallEvent.builder().fromFloor(5).build(),
                CallEvent.builder().fromFloor(6).build()
        );

        ValidationResult result = callEventOutOfBoundValidator.execute(events);

        verify(floorOutOfBoundValidator, times(1)).validate(List.of(5, 6));
        assertFalse(result.error);

    }

}
