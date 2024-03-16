package com.example.doantotnghiep.Services.Position;

import com.example.doantotnghiep.Models.Position;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface PositionService {

    List<Position> listAll();
    Position findById(Integer id);
    Position get(Integer id);

    Position delete(Integer id);

    Position save(Position position);

    Position saveAfterCheck(Position position);

    List<Position> findByPositionName (String positionName);

   boolean existsByPositionName (String positionName);
    Position updatePosition(Integer id, Position positionUpdate);

    List<Position> searchPosition(String positionName);

    Position addPosition (Position position);
}
