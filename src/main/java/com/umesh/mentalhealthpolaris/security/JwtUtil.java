package com.umesh.mentalhealthpolaris.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.umesh.mentalhealthpolaris.model.CustomUserDetails;
import com.umesh.mentalhealthpolaris.model.User;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private String secretKey = "Test123";
    private long expirationTime = 3600000;

    // Method to generate JWT token
    public String generateToken(User userDetails) {
        CustomUserDetails customUserDetails = new CustomUserDetails(userDetails);

        // Convert the list of authorities to a list of strings
        List<String> roles = customUserDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        // Create JWT Algorithm using the secret key
        Algorithm algorithm = Algorithm.HMAC512(secretKey);

        // Build and sign the JWT
        return JWT.create()
                .withSubject(customUserDetails.getUsername())  // Set subject (username)
                .withClaim("roles", roles)                     // Set roles as a claim
                .withIssuedAt(new Date())                      // Set issue date
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))  // Set expiration
                .sign(algorithm);  // Sign the JWT using HMAC512 and the secret key
    }

    // Method to validate JWT token
    public boolean validateToken(String token) {
        try {
            // Create JWT verifier using the same secret key
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();  // Create the verifier

            // Verify the token
            verifier.verify(token);  // If it passes, it's valid
            return true;
        } catch (Exception ex) {
            // If validation fails
            return false;
        }
    }

    // Method to get the username from the JWT token
    public String getUsernameFromToken(String token) {
        // Create JWT algorithm and verifier
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // Parse and return the subject (username) from the token
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    // Method to resolve the token from the Authorization header in the HTTP request
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + bearer);  // Add this for debugging
//        System.out.println("Authorization Header: " + bearer.substring(7));  // Add this for debugging
        if (bearer != null && bearer.startsWith("Bearer ")) {
            // Return token without the "Bearer " prefix
            return bearer.substring(7);
        }
        return null; // Return null if no valid token is found
    }


}
