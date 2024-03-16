package com.example.doantotnghiep.Services.Project;

import com.example.doantotnghiep.Models.Project;

import java.util.List;

public interface ProjectService {
    List<Project> listAll();
    Project get(Integer id);
    Project delete(Integer id);
    Project save(Project project);
    Project saveAfterCheck(Project project);
    boolean existsByProjectName(String projectName);
    Project updateProject(Integer id, Project projectUpdate);
    List<Project> searchByProjectName(String projectName);
    Project addProject(Project project);

    Project updateDepartment ( Integer id, Project projectUpdateDepartment);
}
