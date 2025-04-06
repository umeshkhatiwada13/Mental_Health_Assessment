package com.umesh.mentalhealthpolaris.controller;

import com.umesh.mentalhealthpolaris.model.User;
import com.umesh.mentalhealthpolaris.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("test")
    public String testApi() {
        return "Hello World";
    }
}
