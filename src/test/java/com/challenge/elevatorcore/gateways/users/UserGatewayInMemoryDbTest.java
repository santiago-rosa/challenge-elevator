package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.dtos.User;
import com.challenge.elevatorcore.entities.keyaccess.users.DbUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserGatewayInMemoryDbTest {

    @Mock
    private UserRepository userRepository;

    private UserGateway userGateway = new UserGatewayInMemoryDb(userRepository);

    @BeforeEach
    void setUp() {
        userGateway = new UserGatewayInMemoryDb(userRepository);
    }

    @Test
    public void testFindById() {
        DbUser mockUser = new DbUser();
        mockUser.setId(12);
        User user = User.builder().id(12).admin(false).build();
        when(userRepository.findById(12)).thenReturn(Optional.of(mockUser));

        Optional<User> result = userGateway.findById(12);

        assertEquals(user, result.get());
    }

    @Test
    public void testAddUser() {
        DbUser mockUserResponse = new DbUser();
        mockUserResponse.setId(12);
        mockUserResponse.setFirstName("Juan");
        mockUserResponse.setAdmin(true);
        User user = User.builder().firstName("Juan").admin(false).build();
        when(userRepository.save(any(DbUser.class))).thenReturn(mockUserResponse);

        User result = userGateway.addUser(user);

        assertEquals(12, (int) result.getId());
    }

    @Test
    public void testFindById_notFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Optional<User> result = userGateway.findById(1);

        assertEquals(Optional.empty(), result);
    }
}
