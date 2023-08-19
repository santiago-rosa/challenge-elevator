package com.challenge.elevatorcore.entities.elevator;

public class ElevatorLock {

    public boolean active;
    public String reason;

    public ElevatorLock(boolean active, String reason) {
        this.active = active;
        this.reason = reason;
    }
}
