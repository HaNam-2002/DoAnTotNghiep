package com.example.doantotnghiep.Repositories;

import com.example.doantotnghiep.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepositories extends JpaRepository<Project, Integer> {
    boolean existsByProjectName(String projectName);


    List<Project> findByProjectNameContaining(String projectName);
}
