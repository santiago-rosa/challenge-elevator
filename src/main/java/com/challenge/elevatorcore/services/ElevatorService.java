package com.challenge.elevatorcore.services;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorWeightEvent;

import java.util.List;

public interface ElevatorService {

    void receiveEvents(List<ElevatorEvent> events);

    void updateWeight(ElevatorWeightEvent event);

}
