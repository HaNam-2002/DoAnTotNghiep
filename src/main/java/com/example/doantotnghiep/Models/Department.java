package com.example.doantotnghiep.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;

    @Column(name = "DepartmentName", columnDefinition =  "nvarchar(50)", nullable = false)
    private  String departmentName;

    @OneToOne
    @JoinColumn(name = "DepartmentManagerId")
    private Employees employees;

}
