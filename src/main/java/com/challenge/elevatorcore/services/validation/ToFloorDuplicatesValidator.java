package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.ToFloorsEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToFloorDuplicatesValidator implements ToFloorEventValidator {

    @Override
    public ValidationResult execute(ToFloorsEvent event) {
        List<Integer> floors = event.getToFloors();
        if (floors.size() != floors.stream().distinct().count()) {
            return new ValidationResult("Repeated select floor events", true);
        }
        return new ValidationResult("", false);
    }
}
