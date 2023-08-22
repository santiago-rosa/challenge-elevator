package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.dtos.User;
import com.challenge.elevatorcore.entities.elevator.PublicElevator;
import com.challenge.elevatorcore.entities.keyaccess.users.DbUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        assertTrue(user.equals(result.get()));
    }

    @Test
    public void testFindById_notFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Optional<User> result = userGateway.findById(1);

        assertEquals(Optional.empty(), result);
    }
}
