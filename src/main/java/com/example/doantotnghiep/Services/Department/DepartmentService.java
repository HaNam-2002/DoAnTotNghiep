package com.example.doantotnghiep.Services.Department;

import com.example.doantotnghiep.Models.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> listAll();

    Department get(Integer id);

    Department delete(Integer id);

    Department save(Department department);

    Department saveAffTerCheck (Department department);

    List<Department> findByDepartmentName (String departmentName);

    boolean  existsByDepartmentManagerId (Integer departmentManagerId);
    boolean exitsByDepartmentName(String departmentName);
     Department addDepartment(Department department);
    Department updateDepartment(Integer id, Department departmentUpdate);
    List<Department> searchDepartment(String departmentName);
}
