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
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;

    @Column(name = "FullName", columnDefinition =  "nvarchar(50)")
    private  String fullName;
    @Column(name = "DateOfBirth", columnDefinition =  "date")
    private Date dateOfBirth;
    @Column(name = "HomeTown", columnDefinition =  "nvarchar(100)")
    private  String homeTown;
    @Column(name = "Phone", columnDefinition =  "int")
    private  int phone;
    @Column(name = "Email", columnDefinition =  "nvarchar(50)")
    private  String email;
    @Column(name = "Nation", columnDefinition =  "nvarchar(50)")
    private  String nation;
    @Column(name = "Gender", columnDefinition =  "nvarchar(50)")
    private  String gender;
    @OneToOne
    @JoinColumn(name = "DepartmentId", columnDefinition = "int")
    private Department department;
    @OneToOne
    @JoinColumn(name = "UserId", columnDefinition = "int")
    private UserEntity userEntity;
    @OneToOne
    @JoinColumn(name = "PositionId", columnDefinition = "int")
    private Position position;


}
