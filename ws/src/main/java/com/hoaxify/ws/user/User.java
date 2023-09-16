package com.hoaxify.ws.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    @NotBlank
    @NotNull(message = "Username must not be empty")
    @Size(min = 4, max = 255, message = "Size must be between 4 and 255")
    private String username;

    @Column(unique = true)
    @NotBlank(message = "E-mail must not be empty")
    @Email(message = "Please enter valid email")
    @Size(min = 5, max = 20, message = "Your email should be at least 5 chars")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String email;

    @NotBlank
    @NotNull(message = "Please enter your password")
    private String password;
}
