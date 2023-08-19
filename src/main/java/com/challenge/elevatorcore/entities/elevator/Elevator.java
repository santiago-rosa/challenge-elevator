package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorType;

import java.math.BigDecimal;
import java.util.List;

public interface Elevator {

    void processEvents(List<ElevatorEvent> events);

    void updateWeight(BigDecimal weight);

    boolean matches(ElevatorType event);

}
