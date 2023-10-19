package com.hoaxify.ws.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"})) //bu annotation ayni alanlara ait birden fazla kayit olamayacagini belirtiyor
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String username;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{hoaxify.constraint.password.pattern}") //"Your password must consist of the characters a-z, A-Z, 0-9."
    private String password;

    @JsonIgnore
    boolean active = false;

    @JsonIgnore
    String activationToken;

    @Lob
    String image;
}
