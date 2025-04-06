package com.umesh.mentalhealthpolaris.utility;

import com.umesh.mentalhealthpolaris.enums.Role;
import com.umesh.mentalhealthpolaris.model.User;
import com.umesh.mentalhealthpolaris.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the admin user already exists in the database
        // If the admin user doesn't exist (isEmpty() returns true), create a default admin
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            // Create and configure the admin user with default credentials
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("adminpassword")); // Encrypt the password using BCrypt
            admin.setRole(Role.ADMIN); // Set the role as ADMIN

            // Save the admin user to the database
            userRepository.save(admin);
            System.out.println("Admin user created"); // Log message confirming admin creation
        } else {
            System.out.println("Admin user already exists"); // Log message if admin already exists
        }
    }
}
