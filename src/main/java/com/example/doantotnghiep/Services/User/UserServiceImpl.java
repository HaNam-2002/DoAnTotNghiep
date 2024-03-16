package com.example.doantotnghiep.Services.User;

import com.example.doantotnghiep.DTOs.PasswordChangeRequest;
import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.UserEntity;
import com.example.doantotnghiep.Repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositories userRepositories;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> listAll() {
        try {
            return userRepositories.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users", e);
        }
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        try {
            return userRepositories.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    @Override
    public UserEntity findById(Integer id) {
        try {
            return userRepositories.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with " + id + " not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error finding user with ID: " + id, e);
        }
    }

    @Override
    public UserEntity delete(Integer id) {
        try {
            UserEntity deleteUser = userRepositories.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("User with " + id + " not found"));
            userRepositories.deleteById(id);
            System.out.println("Xoa thanh cong");
            return deleteUser;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user with ID: " + id, e);
        }
    }

    @Override
    public UserEntity get(Integer id) {
        return findById(id);
    }

    @Override
    public List<UserEntity> findByName(String userName) {
        try {
            List<UserEntity> users = userRepositories.findAllByUserName(userName);
            return users == null ? Collections.emptyList() : users;
        } catch (Exception e) {
            throw new RuntimeException("Error finding user with name: " + userName, e);
        }
    }

    @Override
    public UserEntity saveAfterCheck(UserEntity userEntity) {
        try {
            if (userRepositories.existsByUserName(userEntity.getUserName())) {
                System.out.println("UserName have exist");
                return userEntity;
            } else {
                return userRepositories.save(userEntity);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving user with username: " + userEntity.getUserName(), e);
        }
    }

    @Override
    public UserEntity changePassword(UserEntity userEntity, PasswordChangeRequest passwordChangeRequest) {
        try {
            String oldPassword = passwordChangeRequest.getOldPassword();
            String newPassword = passwordChangeRequest.getNewPassword();

            if (passwordEncoder.matches(oldPassword, userEntity.getPassWord())) {
                userEntity.setPassWord(passwordEncoder.encode(newPassword));
                userRepositories.save(userEntity);
            } else {
                throw new IllegalArgumentException("Invalid old password");
            }
            return userEntity;
        } catch (Exception e) {
            throw new RuntimeException("Error with changePassword ");
        }
    }

}
