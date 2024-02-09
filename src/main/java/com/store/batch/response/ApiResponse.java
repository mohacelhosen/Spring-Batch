package com.store.batch.response;

import lombok.Data;

@Data
public class ApiResponse {

    public static <T> Success<T> success(String endpoint, String requestId, String responseTimestamp , T data, Integer status){
        return new Success<>(endpoint, requestId, responseTimestamp , data, status);
    }

    public static Error error(String endpoint, String requestId, String responseTimestamp , Integer status, String message){
        return new Error(endpoint, requestId, responseTimestamp , status, message);
    }
}