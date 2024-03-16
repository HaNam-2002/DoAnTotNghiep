package com.example.doantotnghiep.Models;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;
    @Column(name = "ProjectName", columnDefinition = "nvarchar(50)", nullable = false)
    private String projectName;
    @Column(name = "Description", columnDefinition = "nvarchar(50)", nullable = false)
    private String description;
    @Column(name = "StartDate", columnDefinition = "date", nullable = false)
    private LocalDate startDate;
    @Column(name = "ProjectStatus", columnDefinition = "nvarchar(50)", nullable = false)
    private String projectStatus;

    @Column(name = "EndDate", columnDefinition = "date", nullable = false)
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "DepartmentId")
    private Department department;

}
