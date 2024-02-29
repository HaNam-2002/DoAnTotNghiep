package com.example.doantotnghiep.Services.Role;

import com.example.doantotnghiep.Models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findById (Integer id);

    List<Role>  listAll();

    Optional<Role> findByRoleName(String roleName);


}
