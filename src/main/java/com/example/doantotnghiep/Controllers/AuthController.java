package com.example.doantotnghiep.Controllers;

import com.example.doantotnghiep.Configs.JWTGenerator;
import com.example.doantotnghiep.DTOs.AuthResponseDTO;
import com.example.doantotnghiep.DTOs.LoginDto;
import com.example.doantotnghiep.DTOs.RegisterDto;
import com.example.doantotnghiep.Models.UserEntity;
import com.example.doantotnghiep.Repositories.RoleRepositories;
import com.example.doantotnghiep.Repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthResponseDTO authResponseDTO;
    private AuthenticationManager authenticationManager;
    private UserRepositories userRepositories;
    private RoleRepositories roleRepositories;
    private PasswordEncoder passwordEncoder;

    private JWTGenerator jwtGenerator;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepositories userRepositories, RoleRepositories roleRepositories, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepositories = userRepositories;
        this.roleRepositories = roleRepositories;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login( @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassWord()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToke(authentication);
        return  new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepositories.existsByUserName(registerDto.getUserName())) {
            return new ResponseEntity<>("User name is taken", HttpStatus.BAD_REQUEST);
        }
        if (registerDto.getPassWordRepeat() != null && !registerDto.getPassWordRepeat().equals(registerDto.getPassWord())) {
            return new ResponseEntity<>("The repeated password is different from the password above", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUserName(registerDto.getUserName());
        user.setPassWord(passwordEncoder.encode(registerDto.getPassWord()));
        user.setRole(roleRepositories.findByRoleName("Employee").get());
        userRepositories.save(user);
        return new ResponseEntity<>("User register success", HttpStatus.OK);
    }

}
