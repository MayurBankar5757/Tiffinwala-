package com.Tiffinwala.TiffinwalaAuthService.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
        return createToken(userDetails);
    }

    private String createToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(grantedAuthority -> grantedAuthority.getAuthority()) // Extract role names
                        .collect(Collectors.toList())) // Ensure this is a List<String>
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Signing with the key
                .compact();
    }
}
