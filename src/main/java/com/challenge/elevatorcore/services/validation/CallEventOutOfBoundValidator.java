package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.CallEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CallEventOutOfBoundValidator implements ElevatorEventValidator {

    private final FloorOutOfBoundValidator floorOutOfBoundValidator;

    @Autowired
    public CallEventOutOfBoundValidator(FloorOutOfBoundValidator floorOutOfBoundValidator) {
        this.floorOutOfBoundValidator = floorOutOfBoundValidator;
    }

    @Override
    public ValidationResult execute(List<CallEvent> events) {
        return floorOutOfBoundValidator.validate(events.stream()
                .map(CallEvent::getFromFloor).toList());
    }

}
