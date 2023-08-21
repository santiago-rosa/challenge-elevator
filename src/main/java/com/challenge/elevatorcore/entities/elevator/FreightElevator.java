package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorType;
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
    public void processEvents(List<ElevatorEvent> events) {
        super.processEvents(events);
    }

    @Override
    protected WeightLimitChecker getWeightLimitChecker() {
        return this.weightLimitChecker;
    }

    @Override
    public boolean matches(ElevatorType event) {
        return FREIGHT.equals(event);
    }

}
