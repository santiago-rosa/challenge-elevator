package com.challenge.elevatorcore.entities.keyaccess;

import com.challenge.elevatorcore.dtos.ToFloorsEvent;

public interface KeyAccessAuthorizer {

    Boolean authorized(ToFloorsEvent event);

}
