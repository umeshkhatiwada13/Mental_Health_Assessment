package com.umesh.mentalhealthpolaris.controller;

import com.umesh.mentalhealthpolaris.dto.AuthRequest;
import com.umesh.mentalhealthpolaris.dto.AuthResponse;
import com.umesh.mentalhealthpolaris.model.Assessment;
import com.umesh.mentalhealthpolaris.response.ApiResponse;
import com.umesh.mentalhealthpolaris.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody AuthRequest request) {
        try {
            // Call the userService's registerUser method and get the message
            HashMap<String, String> response = userService.registerUser(request);

            // create a response with the message and token
            Integer responseCode = Integer.valueOf(response.get("code"));
            return ResponseEntity.ok(new ApiResponse(response.get("message"), responseCode));
        } catch (RuntimeException e) {
            // If the exception is thrown from userService (like "Email already in use")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Error: " + e.getMessage(), 400));
        } catch (Exception e) {
            // General exception handling for any unexpected issues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal server error", 501));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest request) {

        try {
            String token = userService.loginUser(request);
            return ResponseEntity.ok(new ApiResponse("Token fetched Successfully", 200, new AuthResponse(token)));
        } catch (RuntimeException e) {
            // If the exception is thrown from userService (like "Email already in use")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Error: " + e.getMessage(), 400));
        } catch (Exception e) {
            // General exception handling for any unexpected issues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal server error", 501));
        }
    }
}
