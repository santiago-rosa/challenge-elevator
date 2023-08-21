package com.challenge.elevatorcore.controllers;

import com.challenge.elevatorcore.ElevatorApplication;
import com.challenge.elevatorcore.dtos.CallElevatorAction;
import com.challenge.elevatorcore.dtos.ElevatorEvent;
import com.challenge.elevatorcore.services.ElevatorService;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElevatorApplication.class)
@AutoConfigureMockMvc
class ElevatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElevatorService elevatorService;

    @MockBean
    private ElevatorEventMapper eventMapper;

    @Test
    public void post_events() throws Exception {

        when(eventMapper.mapCallEventList(anyList()))
                .thenReturn(Collections.singletonList(ElevatorEvent.builder().build()));

        List<CallElevatorAction> actionList = Collections.singletonList(CallElevatorAction.builder()
                .elevatorType("")
                .fromFloor(6)
                .build());

        mockMvc.perform(post("/api/elevator/calls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(actionList)))
                .andExpect(status().isOk());

        verify(elevatorService, times(1)).receiveEvents(anyList());
        verify(eventMapper, times(1)).mapCallEventList(any());

    }
}
