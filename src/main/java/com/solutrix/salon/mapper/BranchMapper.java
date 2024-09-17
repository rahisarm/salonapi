package com.solutrix.salon.mapper;

import com.solutrix.salon.dto.BranchDTO;
import com.solutrix.salon.entity.Branch;

public class BranchMapper {

    public static BranchDTO maptoBranchDTO(Branch branch) {
        return new BranchDTO(branch.getDocno(),branch.getRefname());
    }

    public static Branch maptoBranch(BranchDTO branchDTO) {
        return new Branch(branchDTO.getDocno(),branchDTO.getRefname());
    }
}
