package com.umesh.mentalhealthpolaris.services;

import com.umesh.mentalhealthpolaris.dto.AuthRequest;
import com.umesh.mentalhealthpolaris.enums.Role;
import com.umesh.mentalhealthpolaris.model.User;
import com.umesh.mentalhealthpolaris.repository.UserRepository;
import com.umesh.mentalhealthpolaris.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public HashMap<String, String> registerUser(AuthRequest request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            // Check if email already exists
            if (userRepository.existsByEmail(request.getEmail())) {
                response.put("message", "Email already exists");
                response.put("code", "409");
            } else {

                // Create new user object
                User user = new User();
                user.setEmail(request.getEmail());
                user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
                user.setRole(Role.USER); // Default role

                // Save the new user to the database
                userRepository.save(user);

                response.put("message", "User registered successfully");
                response.put("code", "200");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String loginUser(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return jwtUtil.generateToken(user); // return JWT
    }
}
