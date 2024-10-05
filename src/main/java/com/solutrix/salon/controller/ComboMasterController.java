package com.solutrix.salon.controller;

import com.solutrix.salon.dto.ComboMasterDTO;
import com.solutrix.salon.entity.ComboMaster;
import com.solutrix.salon.service.ComboMasterService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/combo")
public class ComboMasterController {

    @Autowired
    private ComboMasterService service;

    @GetMapping("/all/{brhid}")
    public List<ComboMaster> getComboMaster(@PathVariable int brhid) {
        return service.getAllComboMasters(brhid);
    }

    @PostMapping
    public ComboMaster createAccount(@RequestBody ComboMasterDTO comboMaster) {
        return service.createComboMaster(comboMaster);
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
