package com.hoaxify.ws.user;


import com.hoaxify.ws.user.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

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

    @NotBlank(message = "Please enter your username")
    @NotNull(message = "Username must not be empty")
    @Size(min = 4, max = 255, message = "Size must be between 4 and 255")
    private String username;

    @Column(unique = true)
    @NotBlank(message = "E-mail must not be empty")
    @Email(message = "Please enter valid email")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Please enter your password" )
    @NotNull(message = "Password must not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "Your password must consist of the characters a-z, A-Z, 0-9.")
    private String password;
}
