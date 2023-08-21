package com.challenge.elevatorcore.entities.keyaccess.users;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "app_user")
@Setter
public class ElevatorUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "admin", columnDefinition = "false by default")
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }
}
