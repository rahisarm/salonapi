package com.solutrix.salon.service;

import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.Vendor;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.AccountRepo;
import com.solutrix.salon.repository.VendorRepo;
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
public class VendorService {

    @Autowired
    private VendorRepo repo;

    @Autowired
    private AccountRepo accountRepo;


    @Autowired
    private AccountService accountService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Vendor> getAllVendors(int brhid) {
        return repo.findAllByBrhidIsAndAndStatusNot(brhid,7);
    }


    public Optional<Vendor> getVendorById(int docno) {
        return repo.findById(docno);
    }

    @Transactional
    public Vendor createVendor(Vendor vendor) {
        entityManager.clear();
        vendor.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(vendor.getBrhid());
        vendor.setDocno(maxDocNo.orElse(0) + 1);
        vendor.setDate(Date.valueOf(LocalDate.now()));
        vendor.setVocno(maxVocNo.orElse(0) + 1);

        Account account = new Account();
        account.setCode("AP-" + vendor.getDocno());
        account.setAccount(vendor.getDocno()+"");
        account.setActype("AP");
        account.setBrhid(vendor.getBrhid());
        account.setAcname(vendor.getRefname());
        account.setUserid(vendor.getUserid());

        account=accountService.createAccount(account);
        vendor.setAcno(account.getDocno());
        return repo.save(vendor);
    }

    @Transactional
    public Vendor updateVendor(int id, Vendor vendor) {
        Vendor vendoritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vendor Not Found"));
        vendoritem.setEmail(vendor.getEmail());
        vendoritem.setMobile(vendor.getMobile());
        vendoritem.setRefname(vendor.getRefname());

        Account account=accountRepo.findById(vendoritem.getAcno()).orElseThrow(()-> new ResourceNotFoundException("Account Not Found"));

        account.setCode("AP-" + vendoritem.getDocno());
        account.setAccount(vendoritem.getDocno()+"");
        account.setActype("AP");
        account.setBrhid(vendoritem.getBrhid());
        account.setAcname(vendoritem.getRefname());
        account.setUserid(vendoritem.getUserid());

        account=accountService.updateAccount(account.getDocno(),account);
        return repo.save(vendoritem);
    }

    @Transactional
    public void deleteVendor(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        Vendor vendoritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Vendor Not Found"));
        Account account=accountRepo.findById(vendoritem.getAcno()).orElseThrow(()-> new ResourceNotFoundException("Account Not Found"));
        accountService.deleteAccount(account.getDocno());
        repo.deleteById(id);
    }

}
