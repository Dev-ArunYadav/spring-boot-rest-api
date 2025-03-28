package com.spring_boot.spring_boot_rest_api.utils;

import java.util.List;

public class ResponseUtil {

    public static <T> APIResponse<T> buildSuccessResponse(String message, T data) {
        return APIResponse.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .errors(null)
                .build();
    }

    public static <T> APIResponse<T> buildErrorResponse(String message, List<ErrorDetails> errors) {
        return APIResponse.<T>builder()
                .status("error")
                .message(message)
                .data(null)
                .errors(errors)
                .build();
    }
}

