package com.example.doantotnghiep.Repositories;

import com.example.doantotnghiep.Models.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepositories extends JpaRepository<Employees, Integer> {

    Optional<Employees> findById(Integer id);

    List<Employees> findByFullNameContaining(String fullName);

    @Query(value = "select * from employees where department_id = :departmentId", nativeQuery = true)
    List<Employees> findEmployeesByDepartmentId(Integer departmentId);

    @Query(value = "select * from employees where position_id = :positionId", nativeQuery = true)
    List<Employees> findEmployeesByPositionId(Integer positionId);



}
