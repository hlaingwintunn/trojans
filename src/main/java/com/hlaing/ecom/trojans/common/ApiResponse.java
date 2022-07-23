package com.hlaing.ecom.trojans.common;

import lombok.Getter;

import java.time.LocalDateTime;

public class ApiResponse {

    @Getter
    private final boolean success;
    @Getter
    private final String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getTimestamp() {
        return LocalDateTime.now().toString();
    }

}
