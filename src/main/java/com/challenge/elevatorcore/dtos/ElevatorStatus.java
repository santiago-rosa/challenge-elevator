package com.challenge.elevatorcore.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public class ElevatorStatus {

    public List<Integer> currentPath;
    public Integer currentPosition;

}
