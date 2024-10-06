package com.solutrix.salon.service;

import com.solutrix.salon.entity.Account;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.AccountRepo;
import com.solutrix.salon.repository.AccountRepo;
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
public class AccountService {

    @Autowired
    private AccountRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Account> getAllAccounts(int brhid) {
        return repo.findAllByBrhidIsAndAndStatusNot(brhid,7);
    }

    public List<Account> getGLAccounts(int brhid) {
        return repo.findAccountsByBrhidIsAndStatusNotAndActypeEqualsIgnoreCase(brhid,7,"GL");
    }

    public List<Account> getARAccounts(int brhid) {
        return repo.findAccountsByBrhidIsAndStatusNotAndActypeEqualsIgnoreCase(brhid,7,"AR");
    }

    public List<Account> getAPAccounts(int brhid) {
        return repo.findAccountsByBrhidIsAndStatusNotAndActypeEqualsIgnoreCase(brhid,7,"AP");
    }

    public List<Account> getHRAccounts(int brhid) {
        return repo.findAccountsByBrhidIsAndStatusNotAndActypeEqualsIgnoreCase(brhid,7,"HR");
    }


    public Optional<Account> getAccountById(int docno) {
        return repo.findById(docno);
    }

    @Transactional
    public Account createAccount(Account account) {
        entityManager.clear();
        account.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(account.getBrhid());
        account.setDocno(maxDocNo.orElse(0) + 1);
        account.setDate(Date.valueOf(LocalDate.now()));
        account.setVocno(maxVocNo.orElse(0) + 1);
        return repo.save(account);
    }

    @Transactional
    public Account updateAccount(int id, Account account) {
        Account accountitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Account Not Found"));
        accountitem.setAccount(account.getAccount());
        accountitem.setAcname(account.getAcname());
        accountitem.setActype(account.getActype());
        accountitem.setCode(account.getCode());
        return repo.save(accountitem);
    }

    @Transactional
    public void deleteAccount(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        repo.deleteById(id);
    }

}
