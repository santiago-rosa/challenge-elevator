package com.challenge.elevatorcore.services.validation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FloorOutOfBoundValidatorTest {

    @Test
    public void testExecuteWithInvalidFloors() {
        assertTrue(new FloorOutOfBoundValidator(10, 0).validate(List.of(11,5,6)).error);
    }

    @Test
    public void testExecuteWithValidFloors() {
        assertFalse(new FloorOutOfBoundValidator(10, 0).validate(List.of(7,8)).error);
    }

}
