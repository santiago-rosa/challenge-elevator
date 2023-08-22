package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.dtos.User;

import java.util.Optional;

public interface UserGateway {

    Optional<User> findById(Integer id);

    User addUser(User user);

    void delete(Integer id);

}
