package com.umesh.mentalhealthpolaris.dto;

import lombok.Data;

@Data
public class AuthRequest {
//    @Email
//    @NotBlank
    private String email;
    private String password;
}
