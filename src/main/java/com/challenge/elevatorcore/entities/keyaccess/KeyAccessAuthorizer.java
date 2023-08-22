package com.challenge.elevatorcore.entities.keyaccess;

import com.challenge.elevatorcore.dtos.ToFloorEvent;

public interface KeyAccessAuthorizer {

    boolean authorized(ToFloorEvent event);

}
