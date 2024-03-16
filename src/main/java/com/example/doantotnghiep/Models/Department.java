package com.example.doantotnghiep.Models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @JoinColumn(name = "DepartmentManagerId")
    private Employees employees;


    public Department(String departmentName, Employees managerId) {
    }
}
