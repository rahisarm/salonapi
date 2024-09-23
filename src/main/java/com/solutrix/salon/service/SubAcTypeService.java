package com.solutrix.salon.service;

import com.solutrix.salon.entity.SubAcType;
import com.solutrix.salon.entity.SubAcType;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.SubAcTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class SubAcTypeService {

    @Autowired
    private SubAcTypeRepo repo;

    @Transactional
    public SubAcType createSubAcType(SubAcType subAcType) {

        subAcType.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(subAcType.getBrhid());

        subAcType.setDocno(maxDocNo.orElse(0) + 1);
        subAcType.setDate(Date.valueOf(LocalDate.now()));
        subAcType.setVocno(maxDocNo.orElse(0) + 1);
        return repo.save(subAcType);
    }

    @Transactional
    public SubAcType updateSubAcType(int id, SubAcType subAcType) {
        SubAcType subactypeitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("SubAcType Not Found"));
        subactypeitem.setTypename(subAcType.getTypename());
        return repo.save(subactypeitem);
    }

    @Transactional
    public void deleteSubAcType(int id) {
        SubAcType subAcTypeitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("SubAcType Not Found"));
        subAcTypeitem.setStatus(7);
        repo.save(subAcTypeitem);
    }
}
