package com.challenge.elevatorcore.services;

import com.challenge.elevatorcore.dtos.User;
import com.challenge.elevatorcore.gateways.users.UserGateway;
import com.challenge.elevatorcore.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserGateway userGateway;

    @Autowired
    public UserServiceImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public User addUser(User user) {
        return userGateway.addUser(user);
    }

    @Override
    public User getUser(Integer id) {
        Optional<User> user = userGateway.findById(id);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UserNotFoundException("User with id " + id + " was not found");
        }
    }

    @Override
    public void deleteUser(Integer id) {
        userGateway.delete(id);
    }

}
