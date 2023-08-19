package com.challenge.elevatorcore.services;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorWeightEvent;
import com.challenge.elevatorcore.entities.elevator.Elevator;
import com.challenge.elevatorcore.services.exceptions.ElevatorServiceException;
import com.challenge.elevatorcore.services.validation.ElevatorEventValidator;
import com.challenge.elevatorcore.services.validation.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElevatorServiceImpl implements ElevatorService {

    private final List<Elevator> elevators;
    private final List<ElevatorEventValidator> eventValidators;

    @Autowired
    public ElevatorServiceImpl(List<Elevator> elevators, List<ElevatorEventValidator> eventValidators) {
        this.elevators = elevators;
        this.eventValidators = eventValidators;
    }

    @Override
    public void receiveEvents(List<ElevatorEvent> events) {
        Optional<ValidationResult> validation = eventValidators.stream()
                .map(val -> val.execute(events)).filter(it -> it.error).findFirst();
        if (validation.isEmpty()) {
            elevators.forEach(elevator -> elevator.processEvents(
                    events.stream().filter(ev -> elevator.matches(ev.getElevatorType())).toList()));
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
