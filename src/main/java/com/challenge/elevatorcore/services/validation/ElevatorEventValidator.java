package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.ElevatorEvent;

import java.util.List;

public interface ElevatorEventValidator {

    ValidationResult execute(List<ElevatorEvent> event);

}
