package com.hoaxify.ws.user.exception;

import com.hoaxify.ws.shared.utils.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotFoundException extends RuntimeException {

    public NotFoundException(long id){
        super(Messages.getMessageForLocale("hoaxify.user.not.found", LocaleContextHolder.getLocale(), id));
    }
}