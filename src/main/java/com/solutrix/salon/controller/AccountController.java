package com.solutrix.salon.controller;

import com.solutrix.salon.entity.Account;
import com.solutrix.salon.service.AccountService;
import com.solutrix.salon.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/all/{brhid}")
    public List<Account> getAccounts(@PathVariable int brhid) {
        return service.getAllAccounts(brhid);
    }

    @GetMapping("/gl/{brhid}")
    public List<Account> getGLAccounts(@PathVariable int brhid) {
        return service.getGLAccounts(brhid);
    }

    @GetMapping("/ap/{brhid}")
    public List<Account> getAPAccounts(@PathVariable int brhid) {
        return service.getAPAccounts(brhid);
    }

    @GetMapping("/ar/{brhid}")
    public List<Account> getARAccounts(@PathVariable int brhid) {
        return service.getARAccounts(brhid);
    }

    @GetMapping("/hr/{brhid}")
    public List<Account> getHRAccounts(@PathVariable int brhid) {
        return service.getHRAccounts(brhid);
    }
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return service.createAccount(account);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<Account> getAccountById(@PathVariable int docno) {
        Optional<Account> account = service.getAccountById(docno);
        return account.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity deleteAccount(@PathVariable int docno) {
        try {
            service.deleteAccount(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Account> updateAccount(@RequestBody Account updatedAccount) {
        try {
            Account updated = service.updateAccount(updatedAccount.getDocno(), updatedAccount);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
