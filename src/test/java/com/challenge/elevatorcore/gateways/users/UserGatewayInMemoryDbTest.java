package com.challenge.elevatorcore.gateways.users;

import com.challenge.elevatorcore.entities.keyaccess.users.ElevatorUser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserGatewayInMemoryDbTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserGateway userGateway = new UserGatewayInMemoryDb(userRepository);

    @Test
    public void testFindById() {
        //Given
        ElevatorUser mockUser = new ElevatorUser();
        mockUser.setId(12);
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        //When
        Optional<ElevatorUser> result = userGateway.findById(1);

        //Then
        assertEquals(Optional.of(mockUser), result);
    }

    @Test
    public void testFindById_notFound() {
        //Given
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        //When
        Optional<ElevatorUser> result = userGateway.findById(1);

        //Then
        assertEquals(Optional.empty(), result);
    }
}
