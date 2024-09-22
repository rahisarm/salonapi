package com.solutrix.salon.controller;

import com.solutrix.salon.entity.Branch;
import com.solutrix.salon.service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/branch")
public class BranchController {

    @Autowired
    private BranchService service;

    @GetMapping
    public List<Branch> getAllBranches() {
        return service.getAllBranches();
    }

    @PostMapping
    public Branch createBranch(@RequestBody Branch branch) {
        return service.createBranch(branch);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<Branch> getBranchById(@PathVariable int docno) {
        Optional<Branch> branch = service.getBranchById(docno);
        return branch.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity<Branch> deleteBranch(@PathVariable int docno) {
        Optional<Branch> branch = service.getBranchById(docno);
        service.deleteBranch(docno);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{docno}")
    public ResponseEntity<Branch> updateBranch(@PathVariable int docno, @RequestBody Branch updatedBranch) {
        try {
            Branch updated = service.updateBranch(docno, updatedBranch);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
