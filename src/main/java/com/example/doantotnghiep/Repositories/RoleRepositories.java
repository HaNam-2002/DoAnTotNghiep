package com.example.doantotnghiep.Repositories;

import com.example.doantotnghiep.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepositories extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
//    @Query("SELECT r FROM Role r WHERE r.id = :id")
//    Role findByRoleId(Integer roleId);
}
