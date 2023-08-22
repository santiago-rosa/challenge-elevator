package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class WeightChangeAction {

    @NotNull
    public BigDecimal measure;
    @NotNull
    public String elevatorType;

}
