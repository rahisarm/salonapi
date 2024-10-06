package com.solutrix.salon.controller;

import com.solutrix.salon.entity.ExpType;
import com.solutrix.salon.service.ExpTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exptype")
public class ExpTypeController {

    @Autowired
    private ExpTypeService service;

    @GetMapping("/all/{brhid}")
    public List<ExpType> getExpTypes(@PathVariable int brhid) {
        return service.getAllExpTypes(brhid);
    }

    @PostMapping
    public ExpType createExpType(@RequestBody ExpType account) {
        return service.createExpType(account);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<ExpType> getExpTypeById(@PathVariable int docno) {
        Optional<ExpType> account = service.getExpTypeById(docno);
        return account.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity deleteExpType(@PathVariable int docno) {
        try {
            service.deleteExpType(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<ExpType> updateExpType(@RequestBody ExpType updatedExpType) {
        try {
            ExpType updated = service.updateExpType(updatedExpType.getDocno(), updatedExpType);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
