package com.example.doantotnghiep.Configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTGenerator {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
       public String  generateToke(Authentication authentication) {
         String userName = authentication.getName();
         Date  curerntDate = new Date();
         Date exprireDate = new Date(curerntDate.getTime() + SecurityConstants.JWT_EXPRITATION);
         String token = Jwts.builder()
                 .setSubject(userName)
                 .setIssuedAt(curerntDate)
                 .setExpiration(exprireDate)
                 .signWith(key, SignatureAlgorithm.HS512)
                 .compact();
           System.out.println("New token :");
           System.out.println(token);
           return token;
       }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }
}
