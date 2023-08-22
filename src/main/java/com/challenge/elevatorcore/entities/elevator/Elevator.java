package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.CallEvent;
import com.challenge.elevatorcore.dtos.ElevatorType;
import com.challenge.elevatorcore.dtos.ToFloorEvent;

import java.math.BigDecimal;
import java.util.List;

public interface Elevator {

    void processCalls(List<CallEvent> events);

    void processToFloor(ToFloorEvent event);

    void updateWeight(BigDecimal weight);

    boolean matches(ElevatorType event);

}
