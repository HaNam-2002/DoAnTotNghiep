package com.example.doantotnghiep.Controllers;

import com.example.doantotnghiep.Services.Role.RoleService;
import com.example.doantotnghiep.Services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

}
