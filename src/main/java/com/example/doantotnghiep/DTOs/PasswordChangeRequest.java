package com.example.doantotnghiep.DTOs;

import lombok.Data;

@Data
public class PasswordChangeRequest {
private  String oldPassword;
private  String newPassword;
}
