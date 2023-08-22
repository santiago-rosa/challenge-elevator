package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.dtos.User;
import com.challenge.elevatorcore.entities.keyaccess.users.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserGatewayInMemoryDb implements UserGateway {

    private final UserRepository userRepository;

    @Autowired
    public UserGatewayInMemoryDb(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return mapUser(userRepository.findById(id));
    }

    @Override
    public User addUser(User user) {
        return mapUser(userRepository.save(mapElevatorUser(user)));
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    private DbUser mapElevatorUser(User user) {
        DbUser dbUser = new DbUser();
        dbUser.setAdmin(user.getAdmin());
        dbUser.setFirstName(user.getFirstName());
        return dbUser;
    }

    private Optional<User> mapUser(Optional<DbUser> optional) {
        if (optional.isPresent()) {
            DbUser user = optional.get();
            return Optional.of(User.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .admin(user.isAdmin())
                    .build());
        }
        return Optional.empty();
    }

    private User mapUser(DbUser dbuser) {
        return User.builder()
                .id(dbuser.getId())
                .firstName(dbuser.getFirstName())
                .admin(dbuser.isAdmin())
                .build();
    }

}
