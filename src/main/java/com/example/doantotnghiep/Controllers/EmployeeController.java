package com.example.doantotnghiep.Controllers;

import com.example.doantotnghiep.DTOs.ResponseData;
import com.example.doantotnghiep.DTOs.ResponseMessage;
import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Employees;
import com.example.doantotnghiep.Services.Employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("get-all")
    public ResponseEntity<List<Employees>> listAll() {
        try {
            List<Employees> employees = employeeService.listAll();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("get/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id) {
        try {
            Employees employee = employeeService.get(id);
            if (employee == null) {
                return new ResponseEntity<>(new ResponseMessage("Employee not found."), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(employee, HttpStatus.OK);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addNewEmployee")
    public ResponseEntity<?> addNewEmployee(@RequestBody Employees employees) {
        try {
            Employees savedEmployee = employeeService.addEmployee(employees);
            return ResponseEntity.ok(savedEmployee);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ResponseMessage("Add new employee fail."), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id,
                                            @RequestBody Employees employeeUpdate) {
        try {
            Employees updateEmployee = employeeService.updateEmployee(id, employeeUpdate);
            return new ResponseEntity<>(new ResponseData(updateEmployee, "Update complete."), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping ("searchEmployeeByName")
    public  ResponseEntity<?> searchEmployeeByName(@RequestParam String fullName) {
            try {
                if (fullName == null ) {
                    return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
               List<Employees> employees = employeeService.findByEmployeeName(fullName);
                return  new ResponseEntity<>(new ResponseData(employees, "Find complete"), HttpStatus.OK);
            }
            catch (ResourceNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
    @GetMapping("searchEmployeeByDepartment/{departmentId}")
    public  ResponseEntity<?> searchEmployeeByDepartment(@PathVariable Integer departmentId) {
        try {
            if (departmentId == null ) {
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Employees> employees = employeeService.findByDepartment(departmentId);
            return  new ResponseEntity<>(new ResponseData(employees, "Find complete"), HttpStatus.OK);
        }
        catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("searchEmployeeByPosition/{positionId}")
    public  ResponseEntity<?> searchEmployeeByPosition(@PathVariable Integer positionId) {
        try {
            if (positionId == null ) {
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Employees> employees = employeeService.findByPosition(positionId);
            return  new ResponseEntity<>(new ResponseData(employees, "Find complete"), HttpStatus.OK);
        }
        catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
