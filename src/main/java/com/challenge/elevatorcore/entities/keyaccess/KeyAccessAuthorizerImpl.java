package com.challenge.elevatorcore.entities.keyaccess;

import com.challenge.elevatorcore.dtos.ToFloorEvent;
import com.challenge.elevatorcore.dtos.User;
import com.challenge.elevatorcore.gateways.users.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class KeyAccessAuthorizerImpl implements KeyAccessAuthorizer {

    private final UserGateway userGateway;
    private final List<Integer> adminAccessFloors;

    @Autowired
    public KeyAccessAuthorizerImpl(@Value("${building.adminAccessFloors}") List<Integer> adminAccessFloors, UserGateway userGateway) {
        this.adminAccessFloors = adminAccessFloors;
        this.userGateway = userGateway;
    }

    @Override
    public boolean authorized(ToFloorEvent event) {
        if (event.getToFloors().stream().anyMatch(adminAccessFloors::contains)) {
            return userIsAdmin(event);
        }
        return true;
    }

    private boolean userIsAdmin(ToFloorEvent event) {
        Optional<User> found = userGateway.findById(event.getAccessKey());
        if (found.isPresent()) {
            User user = found.get();
            if (!user.getAdmin()) {
                System.out.println("Access key number " + event.getAccessKey() + " is not authorized");
                return false;
            }
        }
        return true;
    }

}
