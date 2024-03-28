package com.example.doantotnghiep.Services.User;

import com.example.doantotnghiep.DTOs.PasswordChangeRequest;
import com.example.doantotnghiep.Models.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
   List<UserEntity> listAll();

   UserEntity save(UserEntity userEntity);

   UserEntity findById(Integer id);

   UserEntity delete(Integer id);

   UserEntity get(Integer id);

   List<UserEntity> findByName(String username);

 UserEntity saveAfterCheck (UserEntity userEntity);

   UserEntity changePassword(UserEntity userEntity, PasswordChangeRequest passwordChangeRequest);

   UserEntity getUserByUsername(String username);
   UserDetailsService userDetailsService ();
   Optional<UserEntity> getUserById(Integer userId);



}
