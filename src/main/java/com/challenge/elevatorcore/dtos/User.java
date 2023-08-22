package com.challenge.elevatorcore.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class User {

    public Integer id;
    @NotNull(message = "Name is required")
    public String firstName;
    @NotNull(message = "Access type is required")
    public Boolean admin;

}

