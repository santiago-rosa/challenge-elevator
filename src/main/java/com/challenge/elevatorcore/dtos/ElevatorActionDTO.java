package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ElevatorActionDTO {

    @NotNull
    public String eventType;
    @NotNull
    public String elevatorType;
    public List<Integer> toFloors;
    public Integer fromFloor;
    public Integer accessKey;

}

