package com.Tiffinwala.TiffinwalaAuthService.Services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Extract the token from the "Bearer " prefix
            String email = jwtService.extractEmail(token); // Extract email from token

            // Call the method with only the token
            if (jwtService.isTokenValid(token)) {
                SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(email));
            }
        }

        // Continue the request-response chain
        filterChain.doFilter(request, response);
    }
}
