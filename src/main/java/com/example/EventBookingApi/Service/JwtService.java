package com.example.EventBookingApi.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs: 3600000}")
    private long  expTime;

    public String generateToken(String userName,String role)
    {
        return Jwts.builder()
                .setSubject(userName)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expTime))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public Claims extractClaim(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token)
    {
        try{
            Claims claims = extractClaim(token);
            return claims.getExpiration().after(new Date());
        }catch (Exception ex)
        {
            return false;
        }
    }

    public String extractUserName(String token)
    {
        return extractClaim(token).getSubject();
    }

    public String extractRole(String token)
    {
        return (String) extractClaim(token).get("role");
    }
}
