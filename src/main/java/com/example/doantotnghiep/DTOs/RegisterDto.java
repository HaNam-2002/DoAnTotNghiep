package com.example.doantotnghiep.DTOs;
import lombok.Data;

@Data
public class RegisterDto {
    private  String username;
    private  String password;
    private  String passwordRepeat;
    private Integer employeeID;
}
