package com.hoaxify.ws.auth.exception;

import com.hoaxify.ws.shared.utils.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super(Messages.getMessageForLocale("hoaxify.auth.invalid.credentials", LocaleContextHolder.getLocale()));
    }
}
