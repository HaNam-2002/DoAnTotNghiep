package com.example.doantotnghiep.Services.Role;

import com.example.doantotnghiep.Models.Role;
import com.example.doantotnghiep.Repositories.RoleRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepositories roleRepositories;

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepositories.findById(id);
    }

    @Override
    public List<Role> listAll() {
        return roleRepositories.findAll();
    }

    public Optional<Role> findByRoleName(String roleName) {
        return roleRepositories.findByRoleName(roleName);
    }

}
