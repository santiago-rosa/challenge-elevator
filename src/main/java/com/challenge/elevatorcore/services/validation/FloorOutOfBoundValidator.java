package com.challenge.elevatorcore.services.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FloorOutOfBoundValidator {

    private final Integer maxFloor;
    private final Integer minFloor;

    @Autowired
    public FloorOutOfBoundValidator(@Value("${building.floors.max}") Integer maxFloor,
                                    @Value("${building.floors.min}") Integer minFloor) {
        this.maxFloor = maxFloor;
        this.minFloor = minFloor;
    }

    public ValidationResult validate(List<Integer> floors) {
        if (floors.stream().anyMatch(floor -> (maxFloor < floor) || (floor < minFloor))) {
            return new ValidationResult("Some floors are out of bounds", true);
        }
        return new ValidationResult("", false);
    }

}
