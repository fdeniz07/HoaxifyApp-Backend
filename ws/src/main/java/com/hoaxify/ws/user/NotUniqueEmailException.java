package com.hoaxify.ws.user;

import com.hoaxify.ws.shared.utils.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class NotUniqueEmailException extends RuntimeException {

    public NotUniqueEmailException() {
        super(Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale()));
    }

    public Map<String,String> getValidationErrors(){

        return Collections.singletonMap("email",Messages.getMessageForLocale("hoaxify.constraint.email.notunique", LocaleContextHolder.getLocale()));
    }
}
