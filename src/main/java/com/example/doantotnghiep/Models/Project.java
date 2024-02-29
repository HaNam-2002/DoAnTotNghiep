package com.example.doantotnghiep.Models;
import lombok.*;
import jakarta.persistence.*;

import java.util.Date;

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
    private String ProjectName;
    @Column(name = "Description", columnDefinition = "nvarchar(50)", nullable = false)
    private String description;
    @Column(name = "StartDate", columnDefinition = "date", nullable = false)
    private Date startDate;

    @Column(name = "EndDate", columnDefinition = "date", nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "TeamID", columnDefinition = "int")
    private Team team;

}
