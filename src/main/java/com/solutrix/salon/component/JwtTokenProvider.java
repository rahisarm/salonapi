package com.solutrix.salon.component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {
	@Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-ms}")
    private long jwtExpirationDate;

    // generate JWT token
    public String generateToken(Authentication authentication,int userdocno){

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userdocno", userdocno);
        claims.put("username", username);
        String token = Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    //get User Doc No from Token
    public int getUserDocno(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userdocno", Integer.class);  // Extract userdocno from the token
    }
    // get username from JWT token
    public String getUsername(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody(); // Extract the body (claims)

        //return claims.getSubject();
        return claims.get("username", String.class);
    }

    // validate JWT token
    public boolean validateToken(String token) {
        try {
            // Parse and verify the JWT token
            Jwts.parserBuilder()
                    .setSigningKey(key())  // Use the correct method
                    .build()
                    .parseClaimsJws(token);  // Parses and verifies the token

            return true;  // If no exceptions are thrown, the token is valid
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            // Token is invalid or expired, log the exception if needed
            System.out.println("Invalid or expired token: " + e.getMessage());
            return false;
        }
    }
}
