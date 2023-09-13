package com.hoaxify.ws.user;

import com.hoaxify.ws.shared.GenericMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("api/v1/users")
    GenericMessage createUser(@RequestBody User user) {
        //System.err.println(user);
        userService.save(user);
        return new GenericMessage("User is created");
    }
}
