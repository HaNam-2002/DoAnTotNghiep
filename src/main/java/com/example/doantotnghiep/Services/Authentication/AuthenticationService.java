package com.example.doantotnghiep.Services.Authentication;

import com.example.doantotnghiep.DTOs.JwtAuthenticationResponse;
import com.example.doantotnghiep.DTOs.LoginDto;
import com.example.doantotnghiep.DTOs.RegisterDto;
import com.example.doantotnghiep.Models.UserEntity;

public interface AuthenticationService {
    UserEntity signup(RegisterDto registerDto);
    JwtAuthenticationResponse signin(LoginDto loginDto);
}
