package com.challenge.elevatorcore.entities.elevator;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorType;
import com.challenge.elevatorcore.entities.keyaccess.KeyAccessFilter;
import com.challenge.elevatorcore.entities.validation.WeightLimitChecker;
import com.challenge.elevatorcore.gateways.ElevatorEventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicElevator extends BaseElevator implements Elevator {

    private static final ElevatorType PUBLIC = ElevatorType.PUBLIC;
    private final WeightLimitChecker weightLimitChecker;
    private final KeyAccessFilter keyAccessFilter;

    @Autowired
    public PublicElevator(@Qualifier("publicElevatorWeightLimitChecker") WeightLimitChecker weightLimitChecker,
                          KeyAccessFilter keyAccessFilter,
                          ElevatorEventSource elevatorEventSource) {
        super(elevatorEventSource, PUBLIC.toString());
        this.weightLimitChecker = weightLimitChecker;
        this.keyAccessFilter = keyAccessFilter;
    }

    @Override
    public void processEvents(List<ElevatorEvent> events) {
        super.processEvents(keyAccessFilter.filter(events));
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
