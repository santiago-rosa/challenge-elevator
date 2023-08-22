package com.challenge.elevatorcore.services;

import com.challenge.elevatorcore.dtos.User;

public interface UserService {

    User addUser(User user);

    User getUser(Integer id);

    void deleteUser(Integer id);

}
