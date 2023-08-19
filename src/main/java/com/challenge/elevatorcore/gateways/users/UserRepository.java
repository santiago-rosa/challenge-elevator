package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.entities.keyaccess.users.ElevatorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ElevatorUser, Integer> {

    Optional<ElevatorUser> findById(Integer id);

}
