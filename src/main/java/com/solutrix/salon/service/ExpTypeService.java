package com.solutrix.salon.service;

import com.solutrix.salon.entity.ExpType;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.ExpTypeRepo;
import com.solutrix.salon.repository.ExpTypeRepo;
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
public class ExpTypeService {

    @Autowired
    private ExpTypeRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ExpType> getAllExpTypes(int brhid) {
        return repo.findAllByBrhidIs(brhid);
    }

    public Optional<ExpType> getExpTypeById(int docno) {
        return repo.findById(docno);
    }

    @Transactional
    public ExpType createExpType(ExpType account) {
        entityManager.clear();
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(account.getBrhid());
        account.setDocno(maxDocNo.orElse(0) + 1);
        account.setDate(Date.valueOf(LocalDate.now()));
        account.setVocno(maxVocNo.orElse(0) + 1);
        return repo.save(account);
    }

    @Transactional
    public ExpType updateExpType(int id, ExpType account) {
        ExpType accountitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("ExpType Not Found"));
        accountitem.setRefname(account.getRefname());
        return repo.save(accountitem);
    }

    @Transactional
    public void deleteExpType(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        repo.deleteById(id);
    }

}
