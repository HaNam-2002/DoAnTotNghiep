package com.example.doantotnghiep.Models;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;
    @Column(name = "TaskName", columnDefinition = "nvarchar(50)", nullable = false)
    private String taskName;
    @Column(name = "TaskDescription", columnDefinition = "nvarchar(100)", nullable = false)
    private String taskDescription;
    @Column(name = "StartDate", columnDefinition = "date", nullable = false)
    private LocalDate startDate;
    @Column(name = "EndDate", columnDefinition = "date")
    private LocalDate endDate;
    @Column(name = "TaskStatus", columnDefinition = "nvarchar(50)")
    private String taskStatus;
    @OneToMany
    @JoinColumn(name = "ReceiverID", columnDefinition = "int")
    private List<Employees> employees;
    @OneToOne
    @JoinColumn(name = "ProjectId")
    private Project project;

}
