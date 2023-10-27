package com.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
@Getter
@Setter
public class ServiceResponse<T> {
    protected boolean success;
    protected T result;
    protected List<String> messages;

    public static <T> ServiceResponse ok(T result) {
        var response = new ServiceResponse<>();
        response.success = true;
        response.result = result;
        return response;
    }

    public static <T> ServiceResponse ok() {
        var response = new ServiceResponse<>();
        response.success = true;
        return response;
    }

    public static <T> ServiceResponse error(List<String> messages) {
        var response = new ServiceResponse<>();
        response.success = false;
        response.messages = messages;
        return response;
    }

    public static <T> ServiceResponse error(String message) {
        var response = new ServiceResponse<>();
        response.success = false;
        response.messages = Arrays.asList(message);
        return response;
    }
}
