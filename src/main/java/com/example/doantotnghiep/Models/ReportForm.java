package com.example.doantotnghiep.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReportForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;
    @Column(name = "ReportName", columnDefinition = "nvarchar(50)", nullable = false)
    private String reportName;
    @Column(name = "Reason", columnDefinition = "nvarchar(250)", nullable = false)
    private String reason;
    @Column(name = "ReportDescription", columnDefinition = "nvarchar(250)", nullable = false)
    private String reportDescription;
    @ManyToOne
    @JoinColumn(name = "EmployeeReportId")
    private Employees employees;

}
