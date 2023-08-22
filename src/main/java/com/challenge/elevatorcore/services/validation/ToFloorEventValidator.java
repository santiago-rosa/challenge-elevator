package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.ToFloorsEvent;

public interface ToFloorEventValidator {

    ValidationResult execute(ToFloorsEvent event);

}
