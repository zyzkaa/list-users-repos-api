package com.example.demo;

import org.springframework.http.HttpStatusCode;

public class ErrorResponse {
    private HttpStatusCode status;
    private String message;

    public ErrorResponse(HttpStatusCode status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
