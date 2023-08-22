package com.challenge.elevatorcore.entities.keyaccess;

import com.challenge.elevatorcore.dtos.ToFloorEvent;

public interface KeyAccessAuthorizer {

    Boolean authorized(ToFloorEvent event);

}
