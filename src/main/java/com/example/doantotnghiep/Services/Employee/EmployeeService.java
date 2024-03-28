package com.example.doantotnghiep.Services.Employee;


import com.example.doantotnghiep.Models.Employees;

import java.util.List;

public interface EmployeeService {
    List<Employees> listAll();

    Employees get(Integer id);

    Employees delete(Integer id);

    Employees save(Employees employees);

    List<Employees> findByEmployeeName (String fullName);

//    boolean  existsByUserId(Integer userId);

    Employees addEmployee(Employees employees);

   Employees updateEmployee(Integer id, Employees employees);

   List<Employees> findByPosition (Integer positionId);

   List<Employees> findByDepartment(Integer departmentId);
}
