package com.solutrix.salon.controller;

import com.solutrix.salon.dto.ComboMasterDTO;
import com.solutrix.salon.entity.ComboMaster;
import com.solutrix.salon.service.ComboMasterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/combo")
public class ComboMasterController {

    @Autowired
    private ComboMasterService service;

//    @GetMapping("/list/{brhid}")
//    public List<ComboMasterDTO> getComboList(@PathVariable int brhid) {
//        return service.getComboList(brhid);
//    }
    @GetMapping("/all/{brhid}")
    public List<ComboMaster> getComboMaster(@PathVariable int brhid) {
        return service.getAllComboMasters(brhid);
    }

    @PostMapping
    public ResponseEntity<ComboMaster> createCombo(@RequestBody ComboMasterDTO comboDTO) {
        System.out.println(comboDTO);
        ComboMaster comboMaster = service.createComboMaster(comboDTO);
        return new ResponseEntity<>(comboMaster, HttpStatus.CREATED);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<ComboMaster> getComboMasterById(@PathVariable int docno) {
        Optional<ComboMaster> comboMaster = service.getComboMasterById(docno);
        return comboMaster.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity deleteComboMaster(@PathVariable int docno) {
        try {
            service.deleteComboMaster(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<ComboMaster> updateComboMaster(@RequestBody ComboMaster updatedComboMaster) {
        try {
            ComboMaster updated = service.updateComboMaster(updatedComboMaster.getDocno(), updatedComboMaster);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
