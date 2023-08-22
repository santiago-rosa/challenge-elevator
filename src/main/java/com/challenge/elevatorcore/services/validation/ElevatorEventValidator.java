package com.challenge.elevatorcore.services.validation;

import com.challenge.elevatorcore.dtos.CallEvent;

import java.util.List;

public interface ElevatorEventValidator {

    ValidationResult execute(List<CallEvent> event);

}
