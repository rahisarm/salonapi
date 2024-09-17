package com.solutrix.salon.service;

import com.solutrix.salon.dto.BranchDTO;

import java.util.List;

public interface BranchService {
    BranchDTO createBranch(BranchDTO branchDTO);
    BranchDTO getBranchById(Long id);
    List<BranchDTO> getAllBranches();
}
