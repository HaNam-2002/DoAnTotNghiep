package com.example.doantotnghiep.Controllers;

import com.example.doantotnghiep.DTOs.ResponseData;
import com.example.doantotnghiep.DTOs.ResponseMessage;
import com.example.doantotnghiep.Exceptions.ResourceNotFoundException;
import com.example.doantotnghiep.Models.Position;
import com.example.doantotnghiep.Services.Position.PositionService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("get-all")
    public ResponseEntity<List<Position>> listAll() {
        try {
            List<Position> positions = positionService.listAll();
            return new ResponseEntity<>(positions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getPositionById(@PathVariable Integer id) {
        try {
            Position position = positionService.get(id);
            if (position == null) {
                return new ResponseEntity<>(new ResponseMessage("Position not found."), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(position, HttpStatus.OK);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addNewPosition")
    public ResponseEntity<?> addNewPosition(@RequestBody Position position) {
        try {
            Position positionNew = positionService.saveAfterCheck(position);
            if (positionNew == null) {
                return new ResponseEntity<>(new ResponseMessage("Add new position fail."), HttpStatus.BAD_REQUEST);
            } else {
                return ResponseEntity.ok(positionNew);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePosition/{id}")
    public ResponseEntity<?> updatePosition(@PathVariable Integer id, @RequestBody Position positionUpdate) {
        try {
            Position updatedPosition = positionService.updatePosition(id, positionUpdate);
            return new ResponseEntity<>(new ResponseData(updatedPosition, "Update complete."), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchByPositionName")
    public ResponseEntity<?> searchPosition(@RequestParam String positionName) {
        try {
            List<Position> positions = positionService.searchPosition(positionName);
            if (positions == null) {
                return new ResponseEntity<>(new ResponseMessage("Position not found"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(positions, HttpStatus.OK);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Position deletePosition = positionService.delete(id);
            return new ResponseEntity<>(deletePosition,HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
