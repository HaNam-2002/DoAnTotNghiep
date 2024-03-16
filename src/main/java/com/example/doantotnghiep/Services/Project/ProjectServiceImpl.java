package com.example.doantotnghiep.Services.Project;

import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Department;
import com.example.doantotnghiep.Models.Project;
import com.example.doantotnghiep.Repositories.ProjectRepositories;
import com.example.doantotnghiep.Services.Department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepositories projectRepositories;
    @Autowired
    private DepartmentService departmentService;

    @Override
    public List<Project> listAll() {
        return projectRepositories.findAll();
    }

    @Override
    public Project get(Integer id) {
        try {
            return projectRepositories.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Project with id " + id + " not found"));
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while retrieving Project", e);
        }
    }

    @Override
    public Project delete(Integer id) {
        try {
            Project projectToDelete = projectRepositories.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Project with id " + id + " not found"));
            projectRepositories.deleteById(id);
            System.out.println("Delete successful");
            return projectToDelete;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while deleting Project", e);
        }
    }

    @Override
    public Project save(Project project) {
        return projectRepositories.save(project);
    }

    @Override
    public Project saveAfterCheck(Project project) {
        try {
            if (projectRepositories.existsByProjectName(project.getProjectName())) {
                System.out.println("Project already exists");
                return project;
            } else {
                return projectRepositories.save(project);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving project with ProjectName: " + project.getProjectName(), e);
        }
    }

    @Override
    public boolean existsByProjectName(String projectName) {
        return projectRepositories.existsByProjectName(projectName);
    }

    @Override
    public Project updateProject(Integer id, Project projectUpdate) {
        try {
            if (projectUpdate == null || projectUpdate.getProjectName() == null) {
                throw new IllegalStateException("Invalid project or update data");
            }

            Optional<Project> projectOptional = projectRepositories.findById(id);
            if (!projectOptional.isPresent()) {
                throw new ResourceNotFoundException("Project not found with id: " + id);
            }

            Project project = projectOptional.get();

            if (!project.getProjectName().equals(projectUpdate.getProjectName()) && existsByProjectName(projectUpdate.getProjectName())) {
                throw new DataIntegrityViolationException("Project name is duplicated");
            }

            Department newDepartmentReceiveProject = departmentService.get(projectUpdate.getDepartment().getId());
            if (newDepartmentReceiveProject == null) {
                throw new ResourceNotFoundException("Department not found");
            }
            project.setProjectName(projectUpdate.getProjectName());
            project.setDepartment(newDepartmentReceiveProject);
            project.setProjectStatus(projectUpdate.getProjectStatus());
            project.setDescription(projectUpdate.getDescription());
            project.setEndDate(projectUpdate.getEndDate());
            project.setStartDate(projectUpdate.getStartDate());
            return projectRepositories.save(project);
        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error updating project with id: " + id, e);
        }
    }

    @Override
    public List<Project> searchByProjectName(String projectName) {
        try {
            if (projectName == null) {
                throw new NullPointerException("ProjectName cannot be null");
            } else {
                List<Project> projects = projectRepositories.findByProjectNameContaining(projectName);
                return projects;
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid project name");
        }
    }

    @Override
    public Project addProject(Project project) {
        try {
            LocalDate currentDate = LocalDate.now();
            if (project.getStartDate().isBefore(currentDate)) {
                throw new IllegalArgumentException("Ngày bắt đầu dự án phải lớn hơn hoặc bằng ngày hiện tại");
            }
            if (project.getEndDate().isBefore(project.getStartDate())) {
                throw new IllegalArgumentException("Ngày kết thúc dự án phải lớn hơn ngày bắt đầu dự án");
            }
            if (projectRepositories.existsByProjectName(project.getProjectName())) {
                throw new NullPointerException("Tên dự án đã tồn tại");
            } else {
                return projectRepositories.save(project);
            }
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Lỗi khi lưu dự án", e);
        }
    }

    @Override
    public Project updateDepartment(Integer id, Project projectUpdateDepartment) {
        try {
            if (projectUpdateDepartment == null ) {
                throw new IllegalStateException("Invalid project or update data");
            }
            Optional<Project> projectOptional = projectRepositories.findById(id);
            if (!projectOptional.isPresent()) {
                throw new ResourceNotFoundException("Project not found with id: " + id);
            }
            Project project = projectOptional.get();
            Department newDepartmentReceiveProject = departmentService.get(projectUpdateDepartment.getDepartment().getId());
            if (newDepartmentReceiveProject == null) {
                throw new ResourceNotFoundException("Department not found");
            }
            project.setDepartment(newDepartmentReceiveProject);
            return projectRepositories.save(project);
        } catch (DataIntegrityViolationException e) {
            throw e;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error updating project with id: " + id, e);
        }
    }

}
