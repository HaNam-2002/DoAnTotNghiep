package com.example.doantotnghiep.Models;
import lombok.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;
    @Column(name = "TypeApplication", columnDefinition = "nvarchar(50)", nullable = false)
    private String TtpeApplication;
    @Column(name = "Description", columnDefinition = "nvarchar(50)", nullable = false)
    private String description;
    @Column(name = "StartDate", columnDefinition = "date", nullable = false)
    private Date startDate;
    @Column(name = "EndDate", columnDefinition = "date", nullable = false)
    private Date EndDate;
    @Column(name = "LeaveStatus", columnDefinition = "nvarchar(50)", nullable = false)
    private String leaveStatus;

    @ManyToOne
    @JoinColumn(name = "EmployeId", columnDefinition = "int")
    private Employees employees;

}
