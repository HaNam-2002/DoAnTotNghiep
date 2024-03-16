package com.example.doantotnghiep.Services.Task;

import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Project;
import com.example.doantotnghiep.Models.Task;
import com.example.doantotnghiep.Repositories.TaskRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements  TaskService{
    @Autowired
    private TaskRepositories taskRepositories;

    @Override
    public List<Task> listAll() {
        return taskRepositories.findAll();
    }

    @Override
    public Task get(Integer id) {
        try {
            return taskRepositories.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Task with id " + id + " not found"));
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while retrieving Task", e);
        }
    }
    @Override
    public Task delete(Integer id) {
        try {
            Task taskDelete = taskRepositories.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Project with id " + id + " not found"));
            taskRepositories.deleteById(id);
            System.out.println("Delete successful");
            return taskDelete;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while deleting Project", e);
        }
    }

    @Override
    public Task save(Task task) {
        return taskRepositories.save(task);
    }

    @Override
    public Task saveAfterCheck(Task task) {
        return null;
    }

    @Override
    public boolean existsByTaskName(String taskName) {
        return taskRepositories.existsByTaskName(taskName);
    }

    @Override
    public Task updateTask(Integer id, Task taskUpdate) {
        return null;
    }

    @Override
    public List<Task> searchByTaskName(String taskName) {
        try {
            if (taskName == null) {
                throw new NullPointerException("TaskName cannot be null");
            } else {
                List<Task> tasks = taskRepositories.findByTaskNameContaining(taskName);
                return tasks;
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid task name");
        }
    }

    @Override
    public Task addTask(Integer projectId,Task task) {
        try {
            LocalDate currentDate = LocalDate.now();
            if (task.getStartDate().isBefore(currentDate)) {
                throw new IllegalArgumentException("Ngày bắt đầu công việc phải lớn hơn hoặc bằng ngày hiện tại");
            }
            if (task.getEndDate() != null && task.getEndDate().isBefore(task.getStartDate())) {
                throw new IllegalArgumentException("Ngày kết thúc công việc phải lớn hơn ngày bắt đầu công việc");
            }
            if (taskRepositories.existsByTaskName(task.getTaskName())) {
                throw new IllegalArgumentException("Same task name");
            }
            return taskRepositories.save(task);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Lỗi khi lưu công việc", e);
        }
    }
}
