package com.example.doantotnghiep.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;
    @Column(name = "SignDay", columnDefinition =  "date")
    private Date signDay;
    @Column(name = "StartDay", columnDefinition =  "date")
    private  Date startDay;
    @Column(name = "Content", columnDefinition =  "nvarchar(250)")
    private  String content;
    @Column(name = "Duration", columnDefinition =  "int")
    private  String duration;
    @OneToOne
    @JoinColumn(name = "EmployeeContractId")
    private Employees employees;

}
