package com.example.doantotnghiep.Services.Department;

import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Department;
import com.example.doantotnghiep.Repositories.DepartmentRepositories;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements  DepartmentService{

        private DepartmentRepositories departmentRepositories;

        @Override
        public List<Department> list(Integer id) {
            try {
                if (id == null) {
                    return departmentRepositories.findAll();
                } else {
                    return departmentRepositories.findByParentId(id);
                }
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("Invalid id");
            }
        }

        @Override
        public Department get(Integer id) {
            try {
                return departmentRepositories.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Department", "id", + id));
            } catch (DataAccessException e) {
                throw new RuntimeException("An error occurred while retrieving department", e);
            }
        }

        @Override
        public Department delete(Integer id) {
            try {
                Department department = get(id);
                departmentRepositories.delete(department);
                return department;
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException("Department not found with id: " + id);
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
        public Department saveAfterCheck(Department department) {
            // Perform necessary checks here
            // If checks pass, proceed with saving
            return save(department);
        }

        @Override
        public List<Department> findByName(String departmentName) {
            try {
                if (departmentName == null) {
                    throw new NullPointerException("Department name cannot be null");
                }
                return departmentRepositories.findAllByDepartmentName(departmentName);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("Invalid department name");
            }
        }
    }

}
