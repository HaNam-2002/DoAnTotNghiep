package com.example.doantotnghiep.Repositories;

import com.example.doantotnghiep.Models.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepositories extends JpaRepository<Position, Integer> {

    boolean existsByPositionName(String positionName);


    List<Position> findByPositionNameContaining(String positionName);
}
