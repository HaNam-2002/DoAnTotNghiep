package com.example.doantotnghiep.Models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;

    @Column(name = "TeamName", columnDefinition = "int", nullable = false)
    private Integer teamName;

    @Column(name = "Purpose", columnDefinition = "nvarchar(50)", nullable = false)
    private Integer purpose;

    @ManyToOne
    @JoinColumn(name = "LeaderID", columnDefinition = "int")
    private Employees employees;
    @ManyToOne
    @JoinColumn(name = "DepartmentID", columnDefinition = "int")
    private Department department;
}
