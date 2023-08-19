package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.entities.keyaccess.users.ElevatorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserGatewayInMemoryDb implements UserGateway {

    private List<Integer> adminAccessFloors;
    private UserRepository userRepository;

    @Autowired
    public UserGatewayInMemoryDb(@Value("${building.adminAccessFloors}") List<Integer> adminAccessFloors, UserRepository userRepository) {
        this.adminAccessFloors = adminAccessFloors;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ElevatorUser> findById(Integer id) {
        return userRepository.findById(id);
    }

}
