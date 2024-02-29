package com.example.doantotnghiep.Services.User;

import com.example.doantotnghiep.DTOs.PasswordChangeRequest;
import com.example.doantotnghiep.Models.UserEntity;

import java.util.List;

public interface UserService {
   List<UserEntity> listAll();

   UserEntity save(UserEntity userEntity);

   UserEntity findById(Integer id);

   UserEntity delete(Integer id);

   UserEntity get(Integer id);

   List<UserEntity> findByName(String userName);

   UserEntity saveAfterCheck (UserEntity userEntity);

   UserEntity changePassword(UserEntity userEntity, PasswordChangeRequest passwordChangeRequest);



}
