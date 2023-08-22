package com.challenge.elevatorcore.controllers;

import com.challenge.elevatorcore.ElevatorApplication;
import com.challenge.elevatorcore.dtos.*;
import com.challenge.elevatorcore.services.ElevatorService;
import com.challenge.elevatorcore.services.exceptions.ElevatorServiceException;
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

import java.math.BigDecimal;
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

    @Test
    public void postCalls() throws Exception {

        List<CallEvent> events = Collections.singletonList(CallEvent.builder().build());

        mockMvc.perform(post("/api/elevator/calls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(events)))
                .andExpect(status().isOk());

        verify(elevatorService, times(1)).receiveCalls(anyList());
    }

    @Test
    public void postCallsException() throws Exception {

        doThrow(new ElevatorServiceException("Something went wrong")).when(elevatorService).receiveCalls(anyList());

        List<CallEvent> events = Collections.singletonList(CallEvent.builder().build());

        mockMvc.perform(post("/api/elevator/calls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(events)))
                .andExpect(status().isBadRequest());

        verify(elevatorService, times(1)).receiveCalls(anyList());

    }

    @Test
    public void selectFloors() throws Exception {
        ToFloorsEvent event = ToFloorsEvent.builder()
                .accessKey(1)
                .toFloors(List.of(3))
                .elevatorType(ElevatorType.FREIGHT)
                .build();

        mockMvc.perform(post("/api/elevator/select_floors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(event)))
                .andExpect(status().isOk());

        verify(elevatorService, times(1)).goToFloors(any(ToFloorsEvent.class));
    }

    @Test
    public void weight() throws Exception {
        ElevatorWeightEvent event = ElevatorWeightEvent.builder()
                .weight(new BigDecimal(500))
                .elevatorType(ElevatorType.FREIGHT)
                .build();

        mockMvc.perform(post("/api/elevator/weight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(event)))
                .andExpect(status().isOk());

        verify(elevatorService, times(1)).updateWeight(any(ElevatorWeightEvent.class));
    }

}
