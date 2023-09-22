package com.hoaxify.ws.user.dto;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.validation.UniqueEmail;
import jakarta.validation.constraints.*;

public record UserCreate(
        @NotBlank(message = "{hoaxify.constraint.username.notblank}")
        @Size(min = 4, max = 255)
        String username,

        @NotBlank
        @Email
       // @UniqueEmail
        String email,

        @Size(min = 8, max = 255)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constraint.password.pattern}") //"Your password must consist of the characters a-z, A-Z, 0-9."
        String password
) {

    public User toUser() {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
