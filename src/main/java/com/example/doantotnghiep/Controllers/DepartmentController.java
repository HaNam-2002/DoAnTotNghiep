package com.example.doantotnghiep.Controllers;

import com.example.doantotnghiep.DTOs.ResponseData;
import com.example.doantotnghiep.DTOs.ResponseMessage;
import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Department;
import com.example.doantotnghiep.Models.Employees;
import com.example.doantotnghiep.Services.Department.DepartmentService;
import com.example.doantotnghiep.Services.Employee.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;


    @GetMapping("get-all")
    public ResponseEntity<List<Department>> ListAll() {
        try {
            List<Department> departments = departmentService.listAll();
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Integer id) {
        try {
            Department department = departmentService.get(id);
            if (department == null) {
                return new ResponseEntity<>(new ResponseMessage("Department not found"), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(department, HttpStatus.OK);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addNewDepartment")
    public ResponseEntity<?> addNewDepartment(@Valid @RequestBody Department department) {
        try {
            Department savedDepartment = departmentService.addDepartment(department);
            return ResponseEntity.ok(new ResponseData(savedDepartment, "Add complete."));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("updateDepartment/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Integer id,
                                              @RequestBody Department departmentUpdate) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, departmentUpdate);
            return new ResponseEntity<>(new ResponseData(updatedDepartment, "Update complete."), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @GetMapping("/searchByDepartmentName")
//    public ResponseEntity<?> searchDepartment(@RequestParam String departmentName) {
//        try {
//            List<Department> departments = departmentService.searchDepartment(departmentName);
//            return ResponseEntity.ok(departments);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}

