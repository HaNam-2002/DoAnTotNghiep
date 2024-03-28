package com.example.doantotnghiep.Controllers;

import com.example.doantotnghiep.DTOs.PasswordChangeRequest;
import com.example.doantotnghiep.DTOs.RoleChangeRequest;
import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.UserEntity;
import com.example.doantotnghiep.Repositories.RoleRepositories;
import com.example.doantotnghiep.Services.Token.JwtUtil;
import com.example.doantotnghiep.Services.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepositories roleRepositories;

//    @GetMapping("/getUser")
//    public ResponseEntity<UserEntity> getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
//        UserEntity user =  userService.getUserByUsername(JwtUtil.getUsernameFromJwt(token));
//        Integer userId= user.getId();
//        Integer employeeId = user.getEmployees().getId();
//        Integer departmentId = user.getEmployees().getDepartmentId();
//        return ResponseEntity.ok(user);
//    }
    @GetMapping("/getUser")
    public ResponseEntity<?> getUserById(HttpServletRequest request) {
        try {
            Integer userId = userService.getUserIdByToken(request);
            UserEntity user = userService.findById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("get-all")
    public ResponseEntity<List<UserEntity>> listAllUser() {
        try {
            List<UserEntity> users = userService.listAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
        try {
            UserEntity user = userService.get(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        try {
            UserEntity userEntity = userService.get(id);
            userService.changePassword(userEntity, passwordChangeRequest);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/updateRole/{id}")
    public ResponseEntity<UserEntity> update(@PathVariable Integer id,
                                             @RequestBody RoleChangeRequest roleUpdateRequest) {
        UserEntity user = userService.findById(id);
        user.setRole(roleRepositories.findById(roleUpdateRequest.getRoleId()).orElseThrow());
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
