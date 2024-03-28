package com.example.doantotnghiep.Services.User;

import com.example.doantotnghiep.DTOs.PasswordChangeRequest;
import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.UserEntity;
import com.example.doantotnghiep.Repositories.UserRepositories;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public List<UserEntity> findByName(String username) {
        try {
            List<UserEntity> users = userRepositories.findAllByUsername(username);
            return users == null ? Collections.emptyList() : users;
        } catch (Exception e) {
            throw new RuntimeException("Error finding user with name: " + username, e);
        }
    }

    @Override
    public UserEntity saveAfterCheck(UserEntity userEntity) {
        try {
            if (userRepositories.existsByUsername(userEntity.getUsername())) {
                System.out.println("UserName have exist");
                return userEntity;
            } else {
                return userRepositories.save(userEntity);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving user with username: " + userEntity.getUsername(), e);
        }
    }

    @Override
    public UserEntity changePassword(UserEntity userEntity, PasswordChangeRequest passwordChangeRequest) {
        try {
            String oldPassword = passwordChangeRequest.getOldPassword();
            String newPassword = passwordChangeRequest.getNewPassword();

            if (passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
                userEntity.setPassword(passwordEncoder.encode(newPassword));
                userRepositories.save(userEntity);
            } else {
                throw new IllegalArgumentException("Invalid old password");
            }
            return userEntity;
        } catch (Exception e) {
            throw new RuntimeException("Error with changePassword ");
        }
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        Optional<UserEntity> userOptional = userRepositories.findByUsername(username);
        return userOptional.orElse(null);
    }

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username){
                return (UserDetails) userRepositories.findByUsername(username).
                orElseThrow(() -> new ResourceNotFoundException("User not found "));
            }
        };
    }

    @Override
    public Optional<UserEntity> getUserById(Integer id) {
        return userRepositories.findUserById(id);
    }

}
