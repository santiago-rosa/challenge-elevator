package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.CallEvent;
import com.challenge.elevatorcore.dtos.ElevatorType;
import com.challenge.elevatorcore.dtos.ToFloorsEvent;
import com.challenge.elevatorcore.entities.validation.WeightLimitChecker;
import com.challenge.elevatorcore.gateways.events.ElevatorEventSourceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FreightElevator extends BaseElevator implements Elevator {

    WeightLimitChecker weightLimitChecker;
    private static final ElevatorType FREIGHT = ElevatorType.FREIGHT;

    @Autowired
    public FreightElevator(@Qualifier("freightElevatorWeightLimitChecker") WeightLimitChecker weightLimitChecker,
                           ElevatorEventSourceGateway elevatorEventSource) {
        super(elevatorEventSource, FREIGHT.toString());
        this.weightLimitChecker = weightLimitChecker;
    }

    @Override
    protected WeightLimitChecker getWeightLimitChecker() {
        return this.weightLimitChecker;
    }

    @Override
    public void processCalls(List<CallEvent> events) {
        super.processEvents(events.stream().map(CallEvent::getFromFloor).toList());
    }

    @Override
    public void processToFloor(ToFloorsEvent event) {
        super.processEvents(event.getToFloors());
    }

    @Override
    public boolean matches(ElevatorType event) {
        return FREIGHT.equals(event);
    }

}
