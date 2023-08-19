package com.challenge.elevatorcore.entities.keyaccess;

import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.dtos.ElevatorEventType;
import com.challenge.elevatorcore.entities.keyaccess.users.ElevatorUser;
import com.challenge.elevatorcore.gateways.users.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class KeyAccessFilterImpl implements KeyAccessFilter {

    private UserGateway userGateway;
    private List<Integer> adminAccessFloors;

    @Autowired
    public KeyAccessFilterImpl(@Value("${building.adminAccessFloors}") List<Integer> adminAccessFloors, UserGateway userGateway) {
        this.adminAccessFloors = adminAccessFloors;
        this.userGateway = userGateway;
    }

    @Override
    public List<ElevatorEvent> filter(List<ElevatorEvent> events) {
        events = new ArrayList<>(events);
        Optional<ElevatorEvent> optionalEvent = filterToFloor(events);
        if(optionalEvent.isPresent()) {
            ElevatorEvent event = optionalEvent.get();
            Optional<ElevatorUser> found = userGateway.findById(event.getAccessKey());
            if(found.isPresent()){
                ElevatorUser user = found.get();
                if(!user.isAdmin()){
                    events.remove(event);
                }
            }
        }
        return events;
    }

    private Optional<ElevatorEvent> filterToFloor(List<ElevatorEvent> events){
        return events.stream()
                .filter(it -> ElevatorEventType.SELECT_FLOORS.equals(it.getEventType()))
                .findFirst();
    }

}
