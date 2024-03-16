package com.example.doantotnghiep.Controllers;

import com.example.doantotnghiep.DTOs.ResponseData;
import com.example.doantotnghiep.DTOs.ResponseMessage;
import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Position;
import com.example.doantotnghiep.Models.Project;
import com.example.doantotnghiep.Services.Project.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("get-all")
    public ResponseEntity<?> listAll() {
        try {
           List<Project> projects = projectService.listAll();
           return new ResponseEntity<>(projects, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getProject/{id}")
    public  ResponseEntity<?> getProject(@PathVariable Integer id) {
        try {
            if (id == null ) {
                return  new ResponseEntity<>(new ResponseMessage("id null"), HttpStatus.BAD_REQUEST );
            }
            Project project = projectService.get(id);
            if (project == null) {
                return new ResponseEntity<>(new ResponseMessage("Project not found"), HttpStatus.NOT_FOUND);
            }
             else  {return new ResponseEntity<>(project, HttpStatus.OK); }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addNewProject")
    public ResponseEntity<?> addNewDepartment(@Valid @RequestBody Project project) {
        try {
            Project newProject = projectService.addProject(project);
            return ResponseEntity.ok(new ResponseData(newProject, "Add complete."));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("updateProject/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Integer id,
                                           @RequestBody Project projectUpdate) {
        try {
            Project updateProject = projectService.updateProject(id,projectUpdate);
            return new ResponseEntity<>(new ResponseData(updateProject, "Update complete."), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("searchProject")
    public  ResponseEntity<?> searchProjectByName(@RequestParam String projectName) {
        try {
            List<Project> projects =  projectService.searchByProjectName(projectName);
            if (projects == null) {
                return new ResponseEntity<>(new ResponseMessage("Position not found"), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(projects, HttpStatus.OK);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("changeDpToReceivePr/{id}")
    public ResponseEntity<?> updateProjectDepartment(@PathVariable Integer id,
                                           @RequestBody Project projectUpdateDepartment) {
        try {
            Project updateProjectDp = projectService.updateDepartment(id,projectUpdateDepartment);
            return new ResponseEntity<>(new ResponseData(updateProjectDp, "Update complete."), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
