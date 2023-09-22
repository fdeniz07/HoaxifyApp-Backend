package com.hoaxify.ws.user;

import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.error.ApiError;
import com.hoaxify.ws.shared.utils.Messages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    ResponseEntity<ApiError> body;

//    @Autowired
//    private final MessageSource messageSource;

    @PostMapping("api/v1/users")
    GenericMessage createUser(@Valid @RequestBody User user) {
//    ResponseEntity<?> createUser(@Valid @RequestBody User user) {
//        ApiError apiError = new ApiError();
//        apiError.setPath("/api/v1/users");
//        apiError.setMessage(ErrorMessages.VALIDATION_ERROR);
//        apiError.setStatus(400);
//        Map<String, String> validationErrors = new HashMap<>();
//        if (user.getUsername() == null || user.getUsername().isEmpty()) {
//            validationErrors.put("username", "Username cannot be null");
//        }
//
//        if (user.getEmail() == null || user.getEmail().isEmpty()) {
//            validationErrors.put("email", "Email cannot be null");
//        }
//
//        if (validationErrors.size() > 0) {
//            apiError.setValidationErrors(validationErrors);
//            return ResponseEntity.badRequest().body(apiError);
//        }
        //System.err.println(user);
        userService.save(user);
       // String message = messageSource.getMessage("hoaxify.create.user.success.message",null, LocaleContextHolder.getLocale());
        String message = Messages.getMessageForLocale("hoaxify.create.user.success.message",  LocaleContextHolder.getLocale());
        return new GenericMessage(message);
//        return ResponseEntity.ok(new GenericMessage("User is created"));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST) //Eger geri dönüs kodunu springe birakmak istersek bu annatotionu kullaniyoruz
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
       // String message = messageSource.getMessage("hoaxify.error.validation",null, LocaleContextHolder.getLocale());
        String message =  Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        //apiError.setMessage(ErrorMessages.VALIDATION_ERROR);
        apiError.setStatus(400);
        //!!! Yöntem 1
//        Map<String, String> validationErrors = new HashMap<>();
//        for (var fieldError : exception.getBindingResult().getFieldErrors()) {
//            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
//        }
        //!!! Yöntem 2 - Yukaridaki kodun daha temiz hali
        var validationErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacing) -> existing)); //3.paramtre ile birden fazla validasyonda gecemezse, sadece bir tane validasyon mesaji göstersin
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    ResponseEntity<ApiError> handleMethodArgNotValidEx(NotUniqueEmailException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(400);
        Map<String, String> validationErrors = new HashMap<>();
        //validationErrors.put("Username","This Username already exist");
        apiError.setValidationErrors(exception.getValidationErrors());
        return ResponseEntity.badRequest().body(apiError);
    }
}
