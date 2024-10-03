package com.solutrix.salon.service;

import com.solutrix.salon.entity.ComboMaster;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.ComboDetailRepo;
import com.solutrix.salon.repository.ComboMasterRepo;
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
public class ComboMasterService {

    @Autowired
    private ComboMasterRepo masterRepo;

    @Autowired
    private ComboDetailRepo detailRepo;



    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ComboMasterRepo comboMasterRepo;

    public List<ComboMaster> getAllComboMasters(int brhid) {
        return masterRepo.findAllByBrhidIsAndAndStatusNot(brhid,7);
    }


    public Optional<ComboMaster> getComboMasterById(int docno) {
        return masterRepo.findById(docno);
    }

    @Transactional
    public ComboMaster createComboMaster(ComboMaster comboMaster) {
        entityManager.clear();
        comboMaster.setStatus(3);
        Optional<Integer> maxDocNo = masterRepo.findMaxDocNo();
        Optional<Integer> maxVocNo = masterRepo.findMaxVocNo(comboMaster.getBrhid());
        comboMaster.setDocno(maxDocNo.orElse(0) + 1);
        comboMaster.setDate(Date.valueOf(LocalDate.now()));
        comboMaster.setVocno(maxVocNo.orElse(0) + 1);
        return masterRepo.save(comboMaster);
    }

    @Transactional
    public ComboMaster updateComboMaster(int id, ComboMaster comboMaster) {
        ComboMaster comboMasteritem=masterRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("ComboMaster Not Found"));
        comboMasteritem.setDescription(comboMaster.getDescription());
        comboMasteritem.setAmount(comboMaster.getAmount());
        comboMasteritem.setRefname(comboMaster.getRefname());
        comboMasteritem.setFromdate(comboMaster.getFromdate());
        comboMasteritem.setTodate(comboMaster.getTodate());
        comboMasteritem.setComboDetailList(comboMaster.getComboDetailList());
        return masterRepo.save(comboMasteritem);
    }

    @Transactional
    public void deleteComboMaster(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        comboMasterRepo.deleteById(id);
    }

}
