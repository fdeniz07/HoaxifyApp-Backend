package com.hoaxify.ws.shared.error;

import com.hoaxify.ws.shared.utils.Messages;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST) //Eger geri dönüs kodunu springe birakmak istersek bu annatotionu kullaniyoruz
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        // String message = messageSource.getMessage("hoaxify.error.validation",null, LocaleContextHolder.getLocale());
        String message = Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale());
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

}
