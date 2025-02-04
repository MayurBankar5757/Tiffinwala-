package com.Tiffinwala.TiffinwalaAuthService.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "1234";  // Replace with a secure key

    // Generate JWT Token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Add user email as subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token valid for 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validate JWT Token (single argument)
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extract email from the token
    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    // Get Authentication object
    public UsernamePasswordAuthenticationToken getAuthentication(String email) {
        // You can fetch user details from the database by email and assign roles here
        // For this example, let's assume the user has a "USER" role
        User user = new User(email, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        
        // Return the Authentication token with user details
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
