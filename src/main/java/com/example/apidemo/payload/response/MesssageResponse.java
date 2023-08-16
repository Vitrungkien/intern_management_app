package com.example.apidemo.payload.response;

public class MesssageResponse {
    private String message;

    public MesssageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
