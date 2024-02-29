package com.example.doantotnghiep.Models;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;

    @Column(name = "PositionName", columnDefinition =  "nvarchar(50)")
    private  String positionName;
    @Column(name = "Salary", columnDefinition =  "decimal")
    private BigDecimal salary;

}
