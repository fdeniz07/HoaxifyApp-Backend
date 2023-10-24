package com.hoaxify.ws.shared.error;

import com.hoaxify.ws.auth.exception.AuthenticationException;
import com.hoaxify.ws.shared.utils.Messages;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.InvalidTokenException;
import com.hoaxify.ws.user.exception.NotFoundException;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice //This annotation make usable for all Controller
public class ErrorHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            NotUniqueEmailException.class,
            ActivationNotificationException.class,
            InvalidTokenException.class,
            NotFoundException.class,
            AuthenticationException.class
    })
    ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(exception.getMessage());

        if (exception instanceof MethodArgumentNotValidException) {
            String message = Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale());
            apiError.setMessage(message);
            apiError.setStatus(400);

            var validationErrors = ((MethodArgumentNotValidException) exception)
                    .getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage,
                            (existing, replacing) -> existing)); //3.paramtre ile birden fazla validasyonda gecemezse, sadece bir tane validasyon mesaji göstersin
            apiError.setValidationErrors(validationErrors);
        } else if (exception instanceof NotUniqueEmailException) {
            apiError.setStatus(400);
            apiError.setValidationErrors(((NotUniqueEmailException) exception).getValidationErrors());
        } else if (exception instanceof ActivationNotificationException) {
            apiError.setStatus(502);
        } else if (exception instanceof InvalidTokenException) {
            apiError.setStatus(400);
        } else if (exception instanceof NotFoundException) {
            apiError.setStatus(404);
        } else if (exception instanceof AuthenticationException) {
            apiError.setStatus(401);
        }
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    /// !!! Tekrar eden kodlar yukaridaki gibi kisaltildi
//    @ResponseStatus(HttpStatus.BAD_REQUEST) //Eger geri dönüs kodunu springe birakmak istersek bu annatotionu kullaniyoruz
//        ResponseEntity<ApiError> handleMethodArgNotValidEx (MethodArgumentNotValidException
//        exception, HttpServletRequest request){
//            ApiError apiError = new ApiError();
//            apiError.setPath(request.getRequestURI());
//            // String message = messageSource.getMessage("hoaxify.error.validation",null, LocaleContextHolder.getLocale());
//            String message = Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale());
//            apiError.setMessage(message);
//            //apiError.setMessage(ErrorMessages.VALIDATION_ERROR);
//            apiError.setStatus(400);
    //!!! Yöntem 1
//        Map<String, String> validationErrors = new HashMap<>();
//        for (var fieldError : exception.getBindingResult().getFieldErrors()) {
//            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
//        }

    //!!! Yöntem 2 - Yukaridaki kodun daha temiz hali
//            var validationErrors = exception.getBindingResult().getFieldErrors()
//                    .stream()
//                    .collect(Collectors.toMap(FieldError::getField,
//                            FieldError::getDefaultMessage,
//                            (existing, replacing) -> existing)); //3.paramtre ile birden fazla validasyonda gecemezse, sadece bir tane validasyon mesaji göstersin
//            apiError.setValidationErrors(validationErrors);
//            return ResponseEntity.badRequest().body(apiError);
//        }

//        @ExceptionHandler(NotUniqueEmailException.class)
//        ResponseEntity<ApiError> handleMethodArgNotValidEx (NotUniqueEmailException exception, HttpServletRequest
//        request){
//            ApiError apiError = new ApiError();
//            apiError.setPath("/api/v1/users");
//            apiError.setMessage(exception.getMessage());
//            apiError.setStatus(400);
//            //Map<String, String> validationErrors = new HashMap<>();
//            //validationErrors.put("Username","This Username already exist");
//            apiError.setValidationErrors(exception.getValidationErrors());
//            return ResponseEntity.status(400).body(apiError);
//        }

//        @ExceptionHandler(ActivationNotificationException.class)
//        ResponseEntity<ApiError> handleActivationNotificationException (ActivationNotificationException
//        exception, HttpServletRequest request){
//            ApiError apiError = new ApiError();
//            apiError.setPath("/api/v1/users");
//            apiError.setMessage(exception.getMessage());
//            apiError.setStatus(502);
//            return ResponseEntity.status(502).body(apiError);
//        }

//        @ExceptionHandler(InvalidTokenException.class)
//        ResponseEntity<ApiError> handleInvalidTokenException (InvalidTokenException exception, HttpServletRequest
//        request){
//            ApiError apiError = new ApiError();
//            apiError.setPath(request.getRequestURI());
//            apiError.setMessage(exception.getMessage());
//            apiError.setStatus(400);
//            return ResponseEntity.status(400).body(apiError);
//        }

//        @ExceptionHandler(NotFoundException.class)
//        ResponseEntity<ApiError> handleInvalidTokenException (NotFoundException exception, HttpServletRequest request){
//            ApiError apiError = new ApiError();
//            apiError.setPath(request.getRequestURI());
//            apiError.setMessage(exception.getMessage());
//            apiError.setStatus(400);
//            return ResponseEntity.status(400).body(apiError);
//        }

//        @ExceptionHandler(AuthenticationException.class)
//        ResponseEntity<?> handleAuthenticationException (AuthenticationException exception){
//            ApiError error = new ApiError();
//            error.setPath("/api/v1/auth");
//            error.setStatus(401);
//            error.setMessage(exception.getMessage());
//            return ResponseEntity.status(401).body(error);
//        }
}