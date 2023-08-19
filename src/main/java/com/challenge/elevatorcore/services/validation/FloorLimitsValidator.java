package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FloorLimitsValidator implements ElevatorEventValidator {

    private final Integer maxFloor;
    private final Integer minFloor;

    @Autowired  // This constructor is used by Spring to inject the properties.
    public FloorLimitsValidator(@Value("${building.floors.max}") Integer maxFloor,
                                @Value("${building.floors.min}") Integer minFloor) {
        this.maxFloor = maxFloor;
        this.minFloor = minFloor;
    }

    @Override
    public ValidationResult execute(List<ElevatorEvent> events) {
        List<Integer> floors = new ArrayList<>();
        events.forEach(event -> {
            floors.add(event.getFromFloor());
        });
        if (floors.stream().anyMatch(floor -> (maxFloor < floor) || (floor < minFloor))) {
            return new ValidationResult("Some floors are out of bounds", true);
        }
        return new ValidationResult("", false);
    }

}
