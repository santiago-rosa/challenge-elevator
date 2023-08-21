package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.entities.keyaccess.users.ElevatorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserGatewayInMemoryDb implements UserGateway {

    private UserRepository userRepository;

    @Autowired
    public UserGatewayInMemoryDb(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ElevatorUser> findById(Integer id) {
        return userRepository.findById(id);
    }

}
