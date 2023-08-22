package com.challenge.elevatorcore.services;

import com.challenge.elevatorcore.dtos.CallEvent;
import com.challenge.elevatorcore.dtos.ElevatorWeightEvent;
import com.challenge.elevatorcore.dtos.ToFloorEvent;
import com.challenge.elevatorcore.entities.elevator.Elevator;
import com.challenge.elevatorcore.services.exceptions.ElevatorServiceException;
import com.challenge.elevatorcore.services.validation.ElevatorEventValidator;
import com.challenge.elevatorcore.services.validation.ToFloorEventValidator;
import com.challenge.elevatorcore.services.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElevatorServiceImpl implements ElevatorService {

    private final List<Elevator> elevators;
    private final List<ElevatorEventValidator> callEventValidators;
    private final List<ToFloorEventValidator> toFloorEventValidators;

    @Autowired
    public ElevatorServiceImpl(List<Elevator> elevators, List<ElevatorEventValidator> callEventValidators, List<ToFloorEventValidator> toFloorEventValidators) {
        this.elevators = elevators;
        this.callEventValidators = callEventValidators;
        this.toFloorEventValidators = toFloorEventValidators;
    }

    @Override
    public void receiveCalls(List<CallEvent> events) {
        Optional<ValidationResult> validation = callEventValidators.stream()
                .map(val -> val.execute(events)).filter(it -> it.error).findFirst();
        if (validation.isEmpty()) {
            elevators.forEach(elevator -> elevator.processCalls(events.stream()
                    .filter(ev -> elevator.matches(ev.getElevatorType())).toList()));
        } else {
            throw new ElevatorServiceException(validation.get().cause);
        }
    }

    @Override
    public void goToFloors(ToFloorEvent event) {
        Optional<ValidationResult> validation = toFloorEventValidators.stream()
                .map(val -> val.execute(event)).filter(validationResult -> validationResult.error).findFirst();
        if (validation.isEmpty()) {
            elevators.stream()
                    .filter(it -> it.matches(event.getElevatorType())).findFirst()
                    .ifPresent(elevator -> elevator.processToFloor(event));
        } else {
            throw new ElevatorServiceException(validation.get().cause);
        }
    }

    @Override
    public void updateWeight(ElevatorWeightEvent event) {
        elevators.stream()
                .filter(elevator -> elevator.matches(event.getElevatorType()))
                .findFirst()
                .ifPresent(elevator -> elevator.updateWeight(event.getWeight()));
    }

}
