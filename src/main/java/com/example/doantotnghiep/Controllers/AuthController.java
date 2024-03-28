package com.example.doantotnghiep.Controllers;


import com.example.doantotnghiep.DTOs.JwtAuthenticationResponse;
import com.example.doantotnghiep.DTOs.LoginDto;
import com.example.doantotnghiep.DTOs.RegisterDto;
import com.example.doantotnghiep.Models.Employees;
import com.example.doantotnghiep.Models.UserEntity;
import com.example.doantotnghiep.Services.Authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping ("/register") public ResponseEntity <UserEntity> signup(@RequestBody RegisterDto registerDto){
        return ResponseEntity.ok(authenticationService.signup(registerDto));
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> siginin(
            @RequestBody LoginDto loginDto)
    {

        return  ResponseEntity.ok(authenticationService.signin(loginDto));
    }



}
