package com.example.doantotnghiep.Repositories;

import com.example.doantotnghiep.Models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.doantotnghiep.Models.Employees;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<UserEntity, Integer> {


    @Query("SELECT Employees  FROM UserEntity u WHERE u.id = :userId")
    Optional<Employees> findEmployeeByUserId(Integer userId);

    Optional<UserEntity> findUserById(Integer id);

    List<UserEntity> findAllByUsername(String username);

    Optional<UserEntity> findByUsername(String username);


    boolean existsByUsername(String username);


    Optional<UserEntity> findByEmployees_Id(Integer employeeId);
}
