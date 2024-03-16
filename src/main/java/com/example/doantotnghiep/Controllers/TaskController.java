package com.example.doantotnghiep.Controllers;

import com.example.doantotnghiep.Models.Project;
import com.example.doantotnghiep.Models.Task;
import com.example.doantotnghiep.Services.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @GetMapping("get-all")
    public ResponseEntity<?> listAll() {
        try {
            List<Task> tasks = taskService.listAll();
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
