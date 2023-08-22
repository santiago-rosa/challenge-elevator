package com.challenge.elevatorcore.entities.keyaccess;

import com.challenge.elevatorcore.dtos.ToFloorsEvent;
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
    public Boolean authorized(ToFloorsEvent event) {
        if (event.getToFloors().stream().anyMatch(adminAccessFloors::contains)) {
            if(event.getAccessKey() == null) return false;
            return userIsAdmin(event);
        }
        return true;
    }

    private boolean userIsAdmin(ToFloorsEvent event) {
        Optional<User> found = userGateway.findById(event.getAccessKey());
        if (found.isPresent()) {
            User user = found.get();
            return user.getAdmin();
        }
        return false;
    }

}
