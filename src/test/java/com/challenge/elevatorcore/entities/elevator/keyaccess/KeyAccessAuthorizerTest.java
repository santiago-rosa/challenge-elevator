package com.challenge.elevatorcore.entities.elevator.keyaccess;

import com.challenge.elevatorcore.dtos.ToFloorEvent;
import com.challenge.elevatorcore.dtos.User;
import com.challenge.elevatorcore.entities.keyaccess.KeyAccessAuthorizer;
import com.challenge.elevatorcore.entities.keyaccess.KeyAccessAuthorizerImpl;
import com.challenge.elevatorcore.gateways.users.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.List;
import java.util.Optional;

@SpringBootTest
class KeyAccessAuthorizerTest {

    @Mock
    private UserGateway userGateway;

    private KeyAccessAuthorizer keyAccessAuthorizer;

    @BeforeEach
    void setUp() {

        keyAccessAuthorizer = new KeyAccessAuthorizerImpl(List.of(5, 6), userGateway);
    }

    @Test
    void successAuthorization() {
        ToFloorEvent event = ToFloorEvent.builder()
                .accessKey(3)
                .toFloors(List.of(5))
                .build();
        when(userGateway.findById(3)).thenReturn(Optional.of(User.builder().admin(true).build()));

        assertTrue(keyAccessAuthorizer.authorized(event));
    }

    @Test
    void failedAuthorization() {
        ToFloorEvent event = ToFloorEvent.builder()
                .accessKey(3)
                .toFloors(List.of(5))
                .build();
        when(userGateway.findById(3)).thenReturn(Optional.of(User.builder().admin(false).build()));

        assertFalse(keyAccessAuthorizer.authorized(event));
    }

    @Test
    void notNeededAuthorization() {
        ToFloorEvent event = ToFloorEvent.builder()
                .accessKey(3)
                .toFloors(List.of(7))
                .build();
        when(userGateway.findById(3)).thenReturn(Optional.of(User.builder().admin(false).build()));

        assertTrue(keyAccessAuthorizer.authorized(event));
    }

    @Test
    void userNotFound() {
        ToFloorEvent event = ToFloorEvent.builder()
                .accessKey(3)
                .toFloors(List.of(5))
                .build();
        when(userGateway.findById(3)).thenReturn(Optional.empty());

        assertFalse(keyAccessAuthorizer.authorized(event));
    }

}
