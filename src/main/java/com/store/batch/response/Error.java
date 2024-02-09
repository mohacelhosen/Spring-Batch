package com.store.batch.response;

public class Error {
    private String endpoint;
    private String requestId;
    private String responseTimestamp;
    private Object data;
    private Integer status;
    private String message;

    public Error(String endpoint, String requestId, String responseTimestamp, Integer status, String message) {
        this.endpoint = endpoint;
        this.requestId = requestId;
        this.data = null;
        this.status = status;
        this.responseTimestamp = responseTimestamp;
        this.message = message;
    }
}
