package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.CallEvent;
import com.challenge.elevatorcore.dtos.ElevatorType;
import com.challenge.elevatorcore.dtos.ToFloorsEvent;

import java.math.BigDecimal;
import java.util.List;

public interface Elevator {

    void processCalls(List<CallEvent> events);

    void processToFloor(ToFloorsEvent event);

    void updateWeight(BigDecimal weight);

    boolean matches(ElevatorType event);

}
