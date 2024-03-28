package com.example.doantotnghiep.Services.Authentication;

import com.example.doantotnghiep.DTOs.JwtAuthenticationResponse;
import com.example.doantotnghiep.DTOs.LoginDto;
import com.example.doantotnghiep.DTOs.RegisterDto;
import com.example.doantotnghiep.Models.Employees;
import com.example.doantotnghiep.Models.Role;
import com.example.doantotnghiep.Models.UserEntity;
import com.example.doantotnghiep.Repositories.EmployeeRepositories;
import com.example.doantotnghiep.Repositories.UserRepositories;
import com.example.doantotnghiep.Services.Token.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepositories userRepositories;
    @Autowired
    private EmployeeRepositories employeeRepositories;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;


    @Override
    public UserEntity signup(RegisterDto registerDto) {
        Integer employeeId = registerDto.getEmployeeID();
        System.out.println("Pas: " + registerDto.getEmployeeID());
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        Optional<Employees> optionalEmployees = employeeRepositories.findById(employeeId);
        if (optionalEmployees.isEmpty()) {
            throw new RuntimeException("Không tìm thấy nhân viên với id: " + employeeId);
        }
        Optional<UserEntity> existingUser = userRepositories.findByEmployees_Id(employeeId);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Nhân viên đã được đăng ký với tài khoản khác");
        }
        Employees employees = optionalEmployees.get();
        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmployees(employees);
        Role role = new Role();
        role.setId(3);
        user.setRole(role);
        return userRepositories.save(user);
    }



    @Override
    public JwtAuthenticationResponse signin(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassWord()));
        var user = userRepositories.findByUsername(loginDto.getUserName().strip());
        var jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        return jwtAuthenticationResponse;
    }

}
