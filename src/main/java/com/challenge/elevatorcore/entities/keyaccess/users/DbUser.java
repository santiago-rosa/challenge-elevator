package com.challenge.elevatorcore.entities.keyaccess.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_user")
@Setter
@Getter
public class DbUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "admin", columnDefinition = "boolean default false")
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

}
