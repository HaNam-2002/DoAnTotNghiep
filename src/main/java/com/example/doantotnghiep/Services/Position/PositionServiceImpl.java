package com.example.doantotnghiep.Services.Position;

import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Position;
import com.example.doantotnghiep.Models.UserEntity;
import com.example.doantotnghiep.Repositories.PositionRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionRepositories positionRepositories;
    @Override
    public List<Position> listAll() {
        try {
            return positionRepositories.findAll();
        } catch (Exception e) {
            throw  new RuntimeException("Error retrieving Position");
        }
    }

    @Override
    public Position findById(Integer id) {
        try {
            return  positionRepositories.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Department with" + id + "not found"));
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while retrieving department", e);
        }
    }

    @Override
    public Position get(Integer id) {
        try {
            return  positionRepositories.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Department with" + id + "not found"));
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while retrieving department", e);
        }
    }

    @Override
    public Position delete(Integer id) {
        try {
            Position deletePosition =  positionRepositories.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Position with " + id + " not found"));
            positionRepositories.deleteById(id);
            System.out.println("Xoa thanh cong");
            return deletePosition;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while deleting Position", e);
        }
    }

    @Override
    public Position save(Position position) {
        try {
            return positionRepositories.save(position);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An error occurred while saving Position", e);
        }
    }

    @Override
    public Position saveAfterCheck(Position position) {
        try {
            if (positionRepositories.existsByPositionName(position.getPositionName())) {
                System.out.println("DepartmentName have exits");
                return position;
            } else {
                return positionRepositories.save(position);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving user with positionName: ", e);
        }
    }
    public boolean existsByPositionName(String positionName) {
        return positionRepositories.existsByPositionName(positionName);
    }

    @Override
    public Position updatePosition(Integer id, Position positionUpdate) {
        try {
            Position existingPosition = get(id);
            if (existingPosition == null) {
                throw new ResourceNotFoundException("Position not found with id " + id);
            }
            if (positionUpdate == null) {
                throw new IllegalArgumentException("Body new position have something wrong");
            }

            if (existsByPositionName(positionUpdate.getPositionName())) {
                throw new IllegalStateException("Position name is duplicated");
            }

            positionUpdate.setId(id);
            return save(positionUpdate);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred while updating position", e);
        }
    }

    @Override
    public List<Position> searchPosition(String positionName) {
        try {
            if (positionName == null) {
                throw new IllegalArgumentException("Position name is required");
            }
            return findByPositionName(positionName);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred while searching positions", e);
        }
    }

    @Override
    public Position addPosition(Position position) {
        return  null;
    }


    @Override
    public List<Position> findByPositionName(String positionName) {
        try {
            if (positionName == null) {
                throw new NullPointerException("Department name cannot be null");
            }
            else {
                List<Position> positions = positionRepositories.findByPositionNameContaining(positionName);
                return positions;
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid department name");
        }
    }
}
