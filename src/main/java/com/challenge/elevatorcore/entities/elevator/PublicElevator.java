package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.CallEvent;
import com.challenge.elevatorcore.dtos.ElevatorType;
import com.challenge.elevatorcore.dtos.ToFloorEvent;
import com.challenge.elevatorcore.entities.keyaccess.KeyAccessAuthorizer;
import com.challenge.elevatorcore.entities.validation.WeightLimitChecker;
import com.challenge.elevatorcore.gateways.events.ElevatorEventSourceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicElevator extends BaseElevator implements Elevator {

    private static final ElevatorType PUBLIC = ElevatorType.PUBLIC;
    private final WeightLimitChecker weightLimitChecker;
    private final KeyAccessAuthorizer keyAccessAuthorizer;

    @Autowired
    public PublicElevator(@Qualifier("publicElevatorWeightLimitChecker") WeightLimitChecker weightLimitChecker,
                          KeyAccessAuthorizer keyAccessAuthorizer,
                          ElevatorEventSourceGateway eventSourceGateway) {
        super(eventSourceGateway, PUBLIC.toString());
        this.weightLimitChecker = weightLimitChecker;
        this.keyAccessAuthorizer = keyAccessAuthorizer;
    }

    @Override
    public void processCalls(List<CallEvent> events) {
        super.processEvents(events.stream().map(CallEvent::getFromFloor).toList());
    }

    @Override
    public void processToFloor(ToFloorEvent event) {
        if (keyAccessAuthorizer.authorized(event)) {
            super.processEvents(event.getToFloors());
        }
    }

    @Override
    public boolean matches(ElevatorType event) {
        return PUBLIC.equals(event);
    }

    @Override
    protected WeightLimitChecker getWeightLimitChecker() {
        return this.weightLimitChecker;
    }

}
