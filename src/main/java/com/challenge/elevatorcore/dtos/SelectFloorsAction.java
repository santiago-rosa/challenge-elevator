package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SelectFloorsAction {

    @NotNull(message = "Elevator type must be provided")
    private String elevatorType;
    @NotNull(message = "Target floors must be provided")
    private List<Integer> toFloors;
    @NotNull(message = "Access key must be provided")
    private Integer accessKey;

}

