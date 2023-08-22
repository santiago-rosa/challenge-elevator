package com.challenge.elevatorcore.controllers;

import com.challenge.elevatorcore.ElevatorApplication;
import com.challenge.elevatorcore.dtos.*;
import com.challenge.elevatorcore.services.UserService;
import com.challenge.elevatorcore.services.exceptions.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElevatorApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void addUser() throws Exception {
        User user = User.builder()
                .firstName("Santiago")
                .admin(true)
                .build();
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());

        verify(userService, times(1)).addUser(any(User.class));
    }

    @Test
    public void getUserById() throws Exception {
        mockMvc.perform(get("/api/users/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).getUser(3);
    }

    @Test
    public void getUserByIdNotFound() throws Exception {
        doThrow(new UserNotFoundException("User not found")).when(userService).getUser(3);

        mockMvc.perform(get("/api/users/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).getUser(3);
    }

    @Test
    public void deleteUserById() throws Exception {

        mockMvc.perform(delete("/api/users/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(3);

    }

}
