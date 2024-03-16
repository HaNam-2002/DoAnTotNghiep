package com.example.doantotnghiep.Repositories;

import com.example.doantotnghiep.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepositories extends  JpaRepository<Task,Integer>{

    boolean existsByTaskName(String taskName);

    List<Task> findByTaskNameContaining(String taskName);
}
