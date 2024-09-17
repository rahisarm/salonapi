package com.solutrix.salon.controller;

import com.solutrix.salon.dto.BranchDTO;
import com.solutrix.salon.service.BranchService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        return new ResponseEntity<>(createdBranch, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable("id") Long id) {
        BranchDTO branchDTO=branchService.getBranchById(id);
        return new ResponseEntity<>(branchDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        List<BranchDTO> branches=branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

}
