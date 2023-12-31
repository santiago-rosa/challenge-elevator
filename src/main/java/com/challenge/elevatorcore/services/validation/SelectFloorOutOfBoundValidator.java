package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.ToFloorsEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SelectFloorOutOfBoundValidator implements ToFloorEventValidator {

    private final FloorOutOfBoundValidator floorOutOfBoundValidator;

    @Autowired
    public SelectFloorOutOfBoundValidator(FloorOutOfBoundValidator floorOutOfBoundValidator) {
        this.floorOutOfBoundValidator = floorOutOfBoundValidator;
    }

    @Override
    public ValidationResult execute(ToFloorsEvent event) {
        return floorOutOfBoundValidator.validate(event.getToFloors());
    }

}
