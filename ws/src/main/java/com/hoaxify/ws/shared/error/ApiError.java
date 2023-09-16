package com.hoaxify.ws.shared.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {

    private int status;
    private String message;
    private String path;
    private long timestamp = new Date().getTime();
    private Map<String,String> validationErrors= new HashMap<>();


}
