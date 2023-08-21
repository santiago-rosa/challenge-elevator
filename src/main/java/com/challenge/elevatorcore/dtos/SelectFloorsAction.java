package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SelectFloorsAction {

    @NotNull
    private String elevatorType;
    @NotNull
    private List<Integer> toFloors;
    @NotNull
    private Integer accessKey;

}

