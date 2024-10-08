package com.solutrix.salon.controller;

import com.solutrix.salon.dto.ExpenseDTO;
import com.solutrix.salon.entity.Expense;
import com.solutrix.salon.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @GetMapping("/all/{brhid}")
    public List<ExpenseDTO> getExpenses(@PathVariable int brhid) {
        return service.getAllExpenses(brhid);
    }

    @PostMapping
    public Expense createExpense(@RequestBody ExpenseDTO expenseDTO) {
        return service.createExpense(expenseDTO);
    }
//
//    @GetMapping("/{docno}")
//    public ResponseEntity<Expense> getExpenseById(@PathVariable int docno) {
//        Optional<Expense> account = service.getExpenseById(docno);
//        return account.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
//    }
//
    @DeleteMapping("/{docno}")
    public ResponseEntity deleteExpense(@PathVariable int docno) {
        try {
            service.deleteExpense(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Expense> updateExpense(@RequestBody ExpenseDTO expenseDTO) {
        try {
            Expense updated = service.updateExpense(expenseDTO);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
