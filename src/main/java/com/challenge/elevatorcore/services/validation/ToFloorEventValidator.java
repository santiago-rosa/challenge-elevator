package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.ToFloorEvent;

public interface ToFloorEventValidator {

    ValidationResult execute(ToFloorEvent event);

}
