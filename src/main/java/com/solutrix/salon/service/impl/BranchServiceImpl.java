package com.solutrix.salon.service.impl;

import com.solutrix.salon.dto.BranchDTO;
import com.solutrix.salon.entity.Branch;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.mapper.BranchMapper;
import com.solutrix.salon.repository.BranchRepo;
import com.solutrix.salon.service.BranchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {
    private BranchRepo branchRepo;

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) {

        Branch branch = BranchMapper.maptoBranch(branchDTO);
        Branch savedBranch = branchRepo.save(branch);
        return BranchMapper.maptoBranchDTO(savedBranch);
    }

    @Override
    public BranchDTO getBranchById(Long id) {
         Branch branch=branchRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Branch not found with id:"+id));
        return BranchMapper.maptoBranchDTO(branch);
    }

    @Override
    public List<BranchDTO> getAllBranches() {
        List<Branch> branches=branchRepo.findAll();
        return branches.stream().map((branch)-> BranchMapper.maptoBranchDTO(branch))
                .collect(Collectors.toList());
    }

}
