package com.solutrix.salon.controller;

import com.solutrix.salon.entity.SubAcType;
import com.solutrix.salon.entity.SubAcType;
import com.solutrix.salon.repository.SubAcTypeRepo;
import com.solutrix.salon.service.SubAcTypeService;
import com.solutrix.salon.service.SubAcTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subactype")
public class SubAcTypeController {
    @Autowired
    private SubAcTypeService service;

    @Autowired
    private SubAcTypeRepo repo;

    @GetMapping("/all/{brhid}")
    public Optional<SubAcType> getAllAcSubTypes(@PathVariable int brhid) {
        return repo.findSubAcTypesByBrhidAndStatusNot(brhid,7);
    }

    @PostMapping
    public SubAcType createSubAcType(@RequestBody SubAcType subAcType) {
        return service.createSubAcType(subAcType);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<SubAcType> getSubAcTypeById(@PathVariable int docno) {
        Optional<SubAcType> subactype = repo.findById(docno);
        return subactype.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity<SubAcType> deleteSubAcType(@PathVariable int docno) {
        Optional<SubAcType> subactype = repo.findById(docno);
        service.deleteSubAcType(docno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{docno}")
    public ResponseEntity<SubAcType> updatesubAcType(@PathVariable int docno, @RequestBody SubAcType updatedsubAcType) {
        try {
            SubAcType updated = service.updateSubAcType(docno, updatedsubAcType);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
