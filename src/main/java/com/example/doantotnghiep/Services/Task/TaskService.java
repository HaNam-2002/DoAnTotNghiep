package com.example.doantotnghiep.Services.Task;

import com.example.doantotnghiep.Models.Task;

import java.util.List;

public interface TaskService {
    List<Task> listAll();
    Task get(Integer id);
    Task delete(Integer id);
    Task save(Task Task);
    Task saveAfterCheck(Task Task);
    boolean existsByTaskName(String TaskName);
    Task updateTask(Integer id, Task TaskUpdate);
    List<Task> searchByTaskName(String TaskName);
    Task addTask(Integer projectId,Task Task);
}
