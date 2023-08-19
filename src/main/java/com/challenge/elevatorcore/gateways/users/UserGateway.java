package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.entities.keyaccess.users.ElevatorUser;

import java.util.Optional;

public interface UserGateway {

    Optional<ElevatorUser> findById(Integer id);

}
