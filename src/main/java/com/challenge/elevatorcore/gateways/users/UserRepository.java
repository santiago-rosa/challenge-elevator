package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.entities.keyaccess.users.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DbUser, Integer> {

    Optional<DbUser> findById(Integer id);

    DbUser save(DbUser user);

    void deleteById(Integer id);

}
