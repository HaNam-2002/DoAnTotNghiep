package com.example.doantotnghiep.Repositories;

import com.example.doantotnghiep.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepositories extends JpaRepository<Department, Integer> {

    boolean existsByDepartmentName(String departmentName);

    boolean existsByEmployeesId(Integer employeeId);

    List<Department> findByDepartmentNameContaining(String departmentName);
}
