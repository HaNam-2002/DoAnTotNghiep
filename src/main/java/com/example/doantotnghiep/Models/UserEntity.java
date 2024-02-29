package com.example.doantotnghiep.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;

    @Column(name = "UserName", columnDefinition = "nvarchar(50)", nullable = false)
    private String userName;

    @Column(name = "PassWord", columnDefinition = "nvarchar(255)", nullable = false)
    private String passWord;
    @Enumerated(EnumType.STRING)
    @Column(name = "active_status", nullable = true)
    private ActiveStatus activeStatus;
    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "rID")
    private Role role;
}
enum ActiveStatus {
    ACTIVE,
    INACTIVE
}
