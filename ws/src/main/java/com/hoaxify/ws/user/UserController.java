package com.hoaxify.ws.user;

import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.error.ApiError;
import com.hoaxify.ws.shared.utils.Messages;
import com.hoaxify.ws.user.dto.UserCreate;
import com.hoaxify.ws.user.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    ResponseEntity<ApiError> body;

//    @Autowired
//    private final MessageSource messageSource;

    @PostMapping("api/v1/users")
    GenericMessage createUser(@Valid @RequestBody UserCreate user) {

/* Eski Kullanim
    ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(ErrorMessages.VALIDATION_ERROR);
        apiError.setStatus(400);
        Map<String, String> validationErrors = new HashMap<>();
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            validationErrors.put("username", "Username cannot be null");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            validationErrors.put("email", "Email cannot be null");
        }

        if (validationErrors.size() > 0) {
            apiError.setValidationErrors(validationErrors);
            return ResponseEntity.badRequest().body(apiError);
        }

 */
        //System.err.println(user);
        userService.save(user.toUser());
        // String message = messageSource.getMessage("hoaxify.create.user.success.message",null, LocaleContextHolder.getLocale());
        String message = Messages.getMessageForLocale("hoaxify.create.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
//        return ResponseEntity.ok(new GenericMessage("User is created"));
    }

    @PatchMapping("/api/v1/users/{token}/active")
    GenericMessage activateUser(@PathVariable String token) {
        userService.activateUser(token);
        String message = Messages.getMessageForLocale("hoaxify.activate.user.success.message", LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @GetMapping("/api/v1/users")
    Page<UserDTO> getUsers(Pageable page) {
        return userService.getUsers(page).map(UserDTO::new);
    }

    @GetMapping("/api/v1/users/{id}")
    UserDTO getUserById(@PathVariable long id) {
        return new UserDTO (userService.getUser(id));
    }
}
