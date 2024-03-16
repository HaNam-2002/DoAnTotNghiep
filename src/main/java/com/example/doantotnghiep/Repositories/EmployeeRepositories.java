package com.example.doantotnghiep.Repositories;

import com.example.doantotnghiep.Models.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepositories extends JpaRepository<Employees, Integer> {

    boolean existsByUserEntity_Id(Integer userId);

    List<Employees> findByFullNameContaining(String fullName);

    @Query(value = "select * from employees where department_id = :departmentId", nativeQuery = true)
    List<Employees> findEmployeesByDepartmentId(Integer departmentId);

    @Query(value = "select * from Employees where position_id = :positionId", nativeQuery = true)
    List<Employees> findEmployeesByPositionId(Integer positionId);
}
