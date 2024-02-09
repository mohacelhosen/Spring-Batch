package com.store.batch.response;

import lombok.Data;

@Data
public class Success<T> {
    private String endpoint;
    private String requestId;
    private String responseTimestamp;
    private T data;
    private Integer status;

    public Success(String endpoint, String requestId, String responseTimestamp, T data, Integer status) {
        this.endpoint = endpoint;
        this.requestId = requestId;
        this.data = data;
        this.status = status;
        this.responseTimestamp = responseTimestamp;
    }
}
