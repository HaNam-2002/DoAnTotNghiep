package com.example.doantotnghiep.Services.Employee;

import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Department;
import com.example.doantotnghiep.Models.Employees;
import com.example.doantotnghiep.Models.Position;
import com.example.doantotnghiep.Repositories.DepartmentRepositories;
import com.example.doantotnghiep.Repositories.EmployeeRepositories;
import com.example.doantotnghiep.Repositories.PositionRepositories;
import com.example.doantotnghiep.Services.Department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepositories employeeRepositories;
    @Autowired
    private DepartmentRepositories departmentRepositories;
    @Autowired
    private PositionRepositories positionRepositories;
    @Override
    public List<Employees> listAll() {
        return employeeRepositories.findAll();
    }

    @Override
    public Employees get(Integer id) {
        try {
            return employeeRepositories.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Employee with id " + id + " not found"));
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while retrieving employee", e);
        }
    }


    @Override
    public Employees delete(Integer id) {
        try {
            Employees deleteEmployee = employeeRepositories.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Employee with " + id + " not found"));
            employeeRepositories.deleteById(id);
            System.out.println("Xoa thanh cong");
            return deleteEmployee;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while deleting Position", e);
        }
    }


//    public boolean existsByUserId(Integer userId) {
//        return employeeRepositories.existsByUserEntity_Id(userId);
//    }

    @Override
    public Employees save(Employees employee) {
        try {
//            Integer userId = employee.getUserEntity().getId();
//            if (employeeRepositories.existsByUserEntity_Id(userId)) {
//                System.out.println("User Account have exits");
//                return employee;
//            } else {
                return employeeRepositories.save(employee);
//            }
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while saving department", e);
        }
    }

    @Override
    public List<Employees> findByEmployeeName(String fullName) {
        try {
            if (fullName == null) {
                throw new NullPointerException("Employee name cannot be null");
            }
            return employeeRepositories.findByFullNameContaining(fullName);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid employee name");
        }
    }

    @Override
    public Employees addEmployee(Employees employees) throws ResourceNotFoundException {
        try {
//            if (existsByUserId(employees.getUserEntity().getId())) {
//                throw new DataIntegrityViolationException("User account already exists.");
//            }
            return employeeRepositories.save(employees);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Failed to add new employee due to data integrity issues.", e);
        }
    }

    @Override
    public Employees updateEmployee(Integer id, Employees employeeUpdate) throws ResourceNotFoundException, DataIntegrityViolationException {
        try {
            Optional<Employees> employeesOptional = employeeRepositories.findById(id);
            if (!employeesOptional.isPresent() || employeeUpdate == null) {
                throw new IllegalStateException("Invalid employee or update data");
            }
            Employees employees = employeesOptional.get();
            if (employeeUpdate.getDepartmentId() != null) {
                Optional<Department> newDepartmentOptional = departmentRepositories.findById(employeeUpdate.getDepartmentId());
                if (!newDepartmentOptional.isPresent()) {
                    throw new ResourceNotFoundException("Department not found");
                }
                Department newDepartment = newDepartmentOptional.get();
                employees.setDepartment(newDepartment);
            }
            if (employeeUpdate.getPosition() != null) {
                Optional<Position> newPositionOptional = positionRepositories.findById(employeeUpdate.getPosition().getId());
                if (!newPositionOptional.isPresent()) {
                    throw new ResourceNotFoundException("Position not found");
                }
                Position newPosition = newPositionOptional.get();
                employees.setPosition(newPosition);
            }
            employees.setFullName(employeeUpdate.getFullName());
            employees.setEmail(employeeUpdate.getEmail());
            employees.setDateOfBirth(employeeUpdate.getDateOfBirth());
            employees.setHomeTown(employeeUpdate.getHomeTown());
            employees.setAcademicLevel(employeeUpdate.getAcademicLevel());
            employees.setGender(employeeUpdate.getGender());
            employees.setNation(employeeUpdate.getNation());
            employees.setPhone(employeeUpdate.getPhone());

            return employeeRepositories.save(employees);
        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating employee", e);
        }
    }


    @Override
    public List<Employees> findByPosition(Integer positionId) {
        try {
            if (positionId == null) {
                throw new NullPointerException("Position name cannot be null");
            }
            return employeeRepositories.findEmployeesByPositionId(positionId);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Have eros");
        }
    }

    @Override
    public List<Employees> findByDepartment(Integer departmentId) {
        try {
            if (departmentId == null) {
                throw  new NullPointerException("Department name cannot be null");
            }
            return employeeRepositories.findEmployeesByDepartmentId(departmentId);
        }
        catch (NullPointerException e) {
            throw  new IllegalArgumentException("Have eros");
        }
    }

}


