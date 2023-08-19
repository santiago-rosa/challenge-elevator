package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorEventType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventConsistencyValidator implements ElevatorEventValidator {

    private static final ElevatorEventType SELECT_FLOOR = ElevatorEventType.SELECT_FLOORS;

    @Override
    public ValidationResult execute(List<ElevatorEvent> events) {
        if (events.stream().filter(event -> SELECT_FLOOR.equals(event.getEventType())).toList().size() > 1) {
            return new ValidationResult("Repeated select floor events", true);
        }
        return new ValidationResult("", false);
    }

}
