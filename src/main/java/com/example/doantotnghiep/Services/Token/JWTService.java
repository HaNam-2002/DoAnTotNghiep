package com.example.doantotnghiep.Services.Token;

import com.example.doantotnghiep.Models.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface JWTService {
    String extractUserName(String token);
    String generateToken(Optional<UserEntity> userDetails);

    Integer getUserIdByToken(HttpServletRequest request);

    boolean isTokenValid(String token , UserDetails userDetails);
}
