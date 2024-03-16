package com.example.doantotnghiep.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;

    @Column(name = "Reason", columnDefinition =  "nvarchar(50)")
    private  String reason;
    @Column(name = "DisciplineDescription", columnDefinition =  "nvarchar(50)")
    private String disciplineDescription;

    @Column(name = "DateDecided", columnDefinition = "date")
    private Date dateDecided;
    @ManyToOne
    @JoinColumn(name = "EmployeeDisciplineId", columnDefinition = "int")
    private Employees employees;

}
