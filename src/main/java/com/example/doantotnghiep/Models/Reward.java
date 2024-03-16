package com.example.doantotnghiep.Models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;

    @Column(name = "Reason", columnDefinition =  "nvarchar(50)")
    private  String reason;
    @Column(name = "Content", columnDefinition =  "nvarchar(50)")
    private String content;
    @Column(name = "DateDecided", columnDefinition =  "date")
    private String dateDecided;
    @ManyToOne
    @JoinColumn(name = "EmployeeRewardId", columnDefinition = "int")
    private Employees employees;
}
