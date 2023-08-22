package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.CallEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CallEventOutOfBoundValidator implements ElevatorEventValidator {

    private final FloorOutOfBoundValidator floorOutOfBoundValidator;

    @Autowired
    public CallEventOutOfBoundValidator(FloorOutOfBoundValidator floorOutOfBoundValidator) {
        this.floorOutOfBoundValidator = floorOutOfBoundValidator;
    }

    @Override
    public ValidationResult execute(List<CallEvent> events) {
        List<Integer> floors = new ArrayList<>();
        events.forEach(event -> {
            Optional.ofNullable(event.getFromFloor()).ifPresent(floors::add);
        });
        return floorOutOfBoundValidator.validate(floors);
    }

}
