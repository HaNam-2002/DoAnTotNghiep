package com.example.doantotnghiep.Services.Department;

import com.example.doantotnghiep.Models.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> list(Integer id);

    Department get(Integer id);

    Department delete(Integer id);

    Department save(Department department);

    Department saveAffTerCheck (Department department);

    List<Department> findByName (String departmentName)

}
