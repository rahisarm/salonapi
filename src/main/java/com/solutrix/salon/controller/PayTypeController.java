package com.solutrix.salon.controller;

import com.solutrix.salon.entity.PayType;
import com.solutrix.salon.service.PayTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paytype")
public class PayTypeController {

    @Autowired
    private PayTypeService service;

    @GetMapping("/all")
    public List<PayType> getPayTypes() {
        return service.getAllPayTypes();
    }

    @PostMapping
    public PayType createPayType(@RequestBody PayType account) {
        return service.createPayType(account);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<PayType> getPayTypeById(@PathVariable int docno) {
        Optional<PayType> account = service.getPayTypeById(docno);
        return account.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity deletePayType(@PathVariable int docno) {
        try {
            service.deletePayType(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<PayType> updatePayType(@RequestBody PayType updatedPayType) {
        try {
            PayType updated = service.updatePayType(updatedPayType.getDocno(), updatedPayType);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
