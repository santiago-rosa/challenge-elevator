package com.challenge.elevatorcore.services;

import com.challenge.elevatorcore.dtos.CallEvent;
import com.challenge.elevatorcore.dtos.ElevatorWeightEvent;
import com.challenge.elevatorcore.dtos.ToFloorsEvent;

import java.util.List;

public interface ElevatorService {

    void receiveCalls(List<CallEvent> events);

    void goToFloors(ToFloorsEvent event);

    void updateWeight(ElevatorWeightEvent event);

}
