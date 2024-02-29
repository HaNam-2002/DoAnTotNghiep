package com.example.doantotnghiep.DTOs;

import lombok.Data;

@Data
public class RegisterDto {
    private  String userName;
    private  String passWord;
    private  String passWordRepeat;
}
