package com.solutrix.salon.service;

import com.solutrix.salon.entity.Branch;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.BranchRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Branch> getAllBranches() {
        return repo.findByStatusNot(7);
    }

    public Optional<Branch> getBranchById(int id) {
        return repo.findById(id);
    }

    @Transactional
    public Branch createBranch(Branch branch) {
        entityManager.clear();

        branch.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        System.out.println("Max Doc No: " + maxDocNo.orElse(null));
        branch.setDocno(maxDocNo.orElse(0) + 1);
        branch.setDate(Date.valueOf(LocalDate.now()));
        System.out.println("Doc No:"+branch.getDocno());
        return repo.save(branch);
    }

    @Transactional
    public Branch updateBranch(int id, Branch branch) {
        Branch branchitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Branch Not Found"));
        branchitem.setBranchname(branch.getBranchname());
        branchitem.setMobile(branch.getMobile());
        branchitem.setEmail(branch.getEmail());
        branchitem.setDocno(branchitem.getDocno());
        return repo.save(branchitem);
    }

    @Transactional
    public void deleteBranch(int id) {
        Branch branchitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Branch Not Found"));
        branchitem.setStatus(7);
        repo.save(branchitem);
    }
}
