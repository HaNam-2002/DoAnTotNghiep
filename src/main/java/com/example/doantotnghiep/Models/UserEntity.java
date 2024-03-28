package com.example.doantotnghiep.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int", nullable = false)
    private Integer id;

    @Column(name = "UserName", columnDefinition = "nvarchar(50)", nullable = false)
    private String username;

    @Column(name = "PassWord", columnDefinition = "nvarchar(255)", nullable = false)
    private String password;
    @Column(name = "ActiveStatus", columnDefinition = "nvarchar(255)")
    private String activeStatus;

    @OneToOne(targetEntity = Employees.class)
    @JoinColumn(name = "EmployeeID")
    private Employees employees;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "rID")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getRoleName()));
        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

