package com.example.doantotnghiep.Services.Department;

import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Department;
import com.example.doantotnghiep.Models.Employees;
import com.example.doantotnghiep.Repositories.DepartmentRepositories;
import com.example.doantotnghiep.Repositories.EmployeeRepositories;
import com.example.doantotnghiep.Services.Employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepositories departmentRepositories;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepositories employeeRepositories;


    @Override
    public List<Department> listAll() {
        try {
            return departmentRepositories.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving department", e);
        }
    }


    @Override
    public Department get(Integer id) {
        try {
            return departmentRepositories.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Department with" + id + "not found"));
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while retrieving department", e);
        }
    }

    @Override
    public Department delete(Integer id) {
        try {
            Department deleteDepartment = departmentRepositories.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Department with " + id + " not found"));
            departmentRepositories.deleteById(id);
            System.out.println("Xoa thanh cong");
            return deleteDepartment;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while deleting department", e);
        }
    }

    @Override
    public Department save(Department department) {
        try {
            return departmentRepositories.save(department);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while saving department", e);
        }
    }

    @Override
    public Department saveAffTerCheck(Department department) {
        try {
            if (departmentRepositories.existsByDepartmentName(department.getDepartmentName())) {
                System.out.println("DepartmentName have exits");
                return department;
            } else {
                return departmentRepositories.save(department);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving user with username: " + department.getDepartmentName(), e);
        }
    }

    @Override
    public List<Department> findByDepartmentName(String departmentName) {
        try {
            if (departmentName == null) {
                throw new NullPointerException("Department name cannot be null");
            }
            List<Department> departments = departmentRepositories.findByDepartmentNameContaining(departmentName);
            return departments;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid department name");
        }
    }

    public boolean existsByDepartmentManagerId(Integer employeeId) {
        return departmentRepositories.existsByEmployeesId(employeeId);
    }

    @Override
    public boolean exitsByDepartmentName(String departmentName) {
        return departmentRepositories.existsByDepartmentName(departmentName);
    }

    @Override
    public Department addDepartment(Department department) throws ResourceNotFoundException {
        try {
            Employees managerId = employeeService.get(department.getEmployees().getId());
            if (managerId == null) {
                throw new ResourceNotFoundException("Employee not found");
            }
            department.setEmployees(managerId);
            return departmentRepositories.save(department);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while saving department", e);
        }
    }

    public Department updateDepartment(Integer id, Department departmentUpdate) throws ResourceNotFoundException, DataIntegrityViolationException {
        try {

            Optional<Department> departmentOptional = departmentRepositories.findById(id);
            if (!departmentOptional.isPresent() || departmentUpdate == null || departmentUpdate.getDepartmentName() == null) {
                throw new IllegalStateException("Invalid department or update data");
            }
            Department department = departmentOptional.get();
            if (this.exitsByDepartmentName(departmentUpdate.getDepartmentName())) {
                throw new DataIntegrityViolationException("Department name is duplicated");
            }
             department.setDepartmentName(departmentUpdate.getDepartmentName());
            if (departmentUpdate.getEmployees() != null) {
                Employees newManager = employeeService.get(departmentUpdate.getEmployees().getId());
                if (newManager == null) {
                    throw new ResourceNotFoundException("Employee not found");
                }
                if (this.existsByDepartmentManagerId(newManager.getId())) {
                    throw new DataIntegrityViolationException("Department management is duplicated");
                }
                department.setEmployees(newManager);
            }
            return departmentRepositories.save(department);

        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating department", e);
        }
    }
    @Override
    public List<Department> searchDepartment(String departmentName) throws ResourceNotFoundException {
        try {
            if (departmentName == null) {
                throw new IllegalArgumentException("Department name is required");
            }
            List<Department> departments = departmentRepositories.findByDepartmentNameContaining(departmentName);
            if (departments.isEmpty()) {
                throw new ResourceNotFoundException("Department not found.");
            }
            return departments;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred while searching departments", e);
        }
    }



}

