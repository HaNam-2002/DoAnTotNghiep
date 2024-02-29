package com.example.doantotnghiep.Models;

import lombok.*;
import jakarta.persistence.*;

import java.util.Date;

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
    private Date startDate;

    @Column(name = "EndDate", columnDefinition = "date", nullable = false)
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "ReceiverID", columnDefinition = "int")
    private Employees employees;

    @ManyToOne
    @JoinColumn(name = "TeamID", columnDefinition = "int")
    private Team team;

}
