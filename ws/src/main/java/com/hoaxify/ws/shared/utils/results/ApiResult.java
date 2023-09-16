package com.hoaxify.ws.shared.utils.results;

import java.util.List;

public class ApiResult<T> {

    private boolean success;
    private String message;
    private String InternalMessage;
    private T Data;
    private List<String> Errors;
}
