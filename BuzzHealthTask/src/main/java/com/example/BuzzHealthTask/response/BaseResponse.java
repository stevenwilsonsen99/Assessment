package com.example.BuzzHealthTask.response;

public record BaseResponse<T>(
        boolean success,
        String message,
        T data,
        int statusCode
) {

    public static <T> BaseResponse<T> success(String message, T data, int statusCode) {
        return new BaseResponse<>(
                true,
                message,
                data,
                statusCode
        );
    }

    public static <T> BaseResponse<T> failure(String message, int statusCode) {
        return new BaseResponse<>(
                false,
                message,
                null,
                statusCode
        );
    }
}
