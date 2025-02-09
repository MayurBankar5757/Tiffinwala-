package com.Tiffinwala.TiffinwalaAuthService.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.Tiffinwala.TiffinwalaAuthService.Entities.User;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long jwtExpirationInMs;

    // Decode the secret and create a signing key
    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8); // Convert secret to byte[]
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName()); // Create Key
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract roles from the token
    public List<String> extractRoles(String token) {
        List<?> roles = extractClaim(token, claims -> claims.get("roles", List.class)); // Extract roles from JWT
        return roles != null ? roles.stream()
                .map(role -> role instanceof String ? (String) role : role.toString())
                .collect(Collectors.toList()) : List.of(); // Safely cast and return a list of strings
    }

    // Extract specific claims from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // Use the secret key to sign the JWT
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate the token
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generate token for the user
    public String generateToken(UserDetails userDetails) {
        // Cast to CustomUserDetails since we know the implementation is CustomUserDetails
        if (userDetails instanceof CustomUserDetails) {
            return createToken((CustomUserDetails) userDetails);  // Cast to CustomUserDetails and pass it
        }
        throw new IllegalArgumentException("Invalid UserDetails object");
    }


    private String createToken(CustomUserDetails customUserDetails) {
        User userDetails = customUserDetails.getUser();  // Access the user object directly from CustomUserDetails

        return Jwts.builder()
                .setSubject(userDetails.getEmail()) // Subject is the email
                .claim("roles", customUserDetails.getAuthorities().stream() // Keep the roles as they are in the token
                        .map(grantedAuthority -> grantedAuthority.getAuthority())
                        .collect(Collectors.toList())) 
                .claim("uid", userDetails.getUid()) // User ID
                .claim("fname", userDetails.getFname()) // First name
                .claim("lname", userDetails.getLname()) // Last name
                .claim("contact", userDetails.getContact()) // Contact number
                .claim("address", userDetails.getAddress()) // Address object
                .setIssuedAt(new Date()) // Issue date
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)) // Set expiration time
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Signing the JWT with the secret key
                .compact(); // Create and return the JWT token
    }

}
