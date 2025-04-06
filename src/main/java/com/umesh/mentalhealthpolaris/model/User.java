package com.umesh.mentalhealthpolaris.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umesh.mentalhealthpolaris.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    @Email(message = "Email should be in a valid format")
    private String email;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // USER or ADMIN

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Assessment> assessments;
}
