package com.example.doantotnghiep.Services.Token;

import com.example.doantotnghiep.Models.UserEntity;
import com.example.doantotnghiep.Repositories.UserRepositories;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements  JWTService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Autowired
    private UserRepositories userRepositories;

    @Override
    public String generateToken(Optional<UserEntity> userDetails) {
        return Jwts.builder().setSubject(userDetails.get().getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getSiginKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSiginKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String extractUserName(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
   @Override
    public Integer getUserIdByToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println(token);
            String userName = extractClaim(token, Claims::getSubject);
            System.out.println(userName);
            Optional<UserEntity> userOptional = userRepositories.findByUsername(userName);
            if (userOptional.isPresent()) {
                UserRepositories user = userOptional.get();
                return user.getId();
            }
        }
        return -1;
    }
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
