package com.challenge.elevatorcore.entities.keyaccess.users;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class ElevatorUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "admin", columnDefinition = "boolean default false")
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }
}
