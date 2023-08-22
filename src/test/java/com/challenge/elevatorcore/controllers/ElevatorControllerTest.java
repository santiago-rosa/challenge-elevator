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

    @MockBean
    private ElevatorEventMapper eventMapper;

    @Test
    public void postCalls() throws Exception {

        when(eventMapper.mapCallEventList(anyList()))
                .thenReturn(Collections.singletonList(CallEvent.builder().build()));

        List<CallElevatorAction> actionList = Collections.singletonList(CallElevatorAction.builder()
                .elevatorType("")
                .fromFloor(6)
                .build());

        mockMvc.perform(post("/api/elevator/calls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(actionList)))
                .andExpect(status().isOk());

        verify(elevatorService, times(1)).receiveCalls(anyList());
        verify(eventMapper, times(1)).mapCallEventList(any());

    }

    @Test
    public void postCallsException() throws Exception {

        when(eventMapper.mapCallEventList(anyList()))
                .thenReturn(Collections.singletonList(CallEvent.builder().build()));

        doThrow(new ElevatorServiceException("Something went wrong")).when(elevatorService).receiveCalls(anyList());

        List<CallElevatorAction> actionList = Collections.singletonList(CallElevatorAction.builder()
                .elevatorType("")
                .fromFloor(6)
                .build());

        mockMvc.perform(post("/api/elevator/calls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(actionList)))
                .andExpect(status().isBadRequest());

        verify(elevatorService, times(1)).receiveCalls(anyList());
        verify(eventMapper, times(1)).mapCallEventList(any());

    }

    @Test
    public void selectFloors() throws Exception {
        ToFloorEvent event = ToFloorEvent.builder()
                .build();

        when(eventMapper.mapSelectFloor(any(SelectFloorsAction.class)))
                .thenReturn(event);

        SelectFloorsAction selectFloorsAction = SelectFloorsAction.builder()
                .elevatorType("FREIGHT")
                .toFloors(List.of(45, 67))
                .accessKey(34)
                .build();

        mockMvc.perform(post("/api/elevator/select_floors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(selectFloorsAction)))
                .andExpect(status().isOk());

        verify(elevatorService, times(1)).goToFloors(event);
        verify(eventMapper, times(1)).mapSelectFloor(any(SelectFloorsAction.class));

    }

    @Test
    public void weight() throws Exception {

        WeightChangeAction action = WeightChangeAction.builder()
                .measure(new BigDecimal(500))
                .elevatorType("FREIGHT")
                .build();

        ElevatorWeightEvent event = ElevatorWeightEvent.builder()
                .weight(new BigDecimal(500))
                .elevatorType(ElevatorType.FREIGHT)
                .build();

        when(eventMapper.mapToWeightEvent(any(WeightChangeAction.class)))
                .thenReturn(event);


        mockMvc.perform(post("/api/elevator/weight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(action)))
                .andExpect(status().isOk());

        verify(elevatorService, times(1)).updateWeight(event);
        verify(eventMapper, times(1)).mapToWeightEvent(any(WeightChangeAction.class));

    }

}
