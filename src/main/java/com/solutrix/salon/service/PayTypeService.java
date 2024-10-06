package com.solutrix.salon.service;

import com.solutrix.salon.entity.PayType;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.PayTypeRepo;
import com.solutrix.salon.repository.PayTypeRepo;
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
public class PayTypeService {

    @Autowired
    private PayTypeRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<PayType> getAllPayTypes() {
        return repo.findAll();
    }

    public Optional<PayType> getPayTypeById(int docno) {
        return repo.findById(docno);
    }

    @Transactional
    public PayType createPayType(PayType account) {
        entityManager.clear();
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        account.setDocno(maxDocNo.orElse(0) + 1);
        return repo.save(account);
    }

    @Transactional
    public PayType updatePayType(int id, PayType account) {
        PayType accountitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("PayType Not Found"));
        accountitem.setRefname(account.getRefname());
        return repo.save(accountitem);
    }

    @Transactional
    public void deletePayType(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        repo.deleteById(id);
    }

}
