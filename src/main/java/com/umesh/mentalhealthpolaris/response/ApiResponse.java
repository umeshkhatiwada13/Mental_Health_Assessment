package com.umesh.mentalhealthpolaris.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private int code; // Custom status or error code
    private T data;

    public ApiResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
