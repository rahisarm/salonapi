package com.solutrix.salon.service;

import com.solutrix.salon.dto.ExpenseDTO;
import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.ExpType;
import com.solutrix.salon.entity.Expense;
import com.solutrix.salon.entity.Jvtran;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.ExpTypeRepo;
import com.solutrix.salon.repository.ExpenseRepo;
import com.solutrix.salon.repository.JvtranRepo;
import com.solutrix.salon.repository.TrnoRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo repo;

    @Autowired
    private TrnoRepo trnoRepo;

    @Autowired
    private JvtranRepo jvrepo;

    @Autowired
    private TrnoService trnoService;


    @PersistenceContext
    private EntityManager entityManager;

    public List<Expense> getAllExpenses(int brhid) {
        return repo.findAllByBrhidIs(brhid);
    }

    @Transactional
    public Expense createExpense(ExpenseDTO expenseDTO) {
        entityManager.clear();

        int trno= trnoService.getNewTrno("EXP");


        Expense expense = new Expense();
        expense.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(expense.getBrhid());
        expense.setDocno(maxDocNo.orElse(0) + 1);
        expense.setDate(Date.valueOf(LocalDate.now()));
        expense.setVocno(maxVocNo.orElse(0) + 1);
        expense.setTrno(trno);  // Assign generated trno

        expense.setExptype(expenseDTO.getExptype());
        expense.setPaytype(expenseDTO.getPaytype());
        expense.setPaytypeno(expenseDTO.getPaytypeno());
        expense.setBillno(expenseDTO.getBillno());
        expense.setVendorid(expenseDTO.getVendorid());
        expense.setAmount(expenseDTO.getAmount());
        expense.setTax(expenseDTO.getTax());
        expense.setNettotal(expenseDTO.getNettotal());
        expense.setRemarks(expenseDTO.getRemarks());
        expense.setUserid(expenseDTO.getUserid());
        expense.setBrhid(expenseDTO.getBrhid());
        expense.setDate(expenseDTO.getDate());
        expense.setExpenseacno(expenseDTO.getExpenseacno());

        repo.save(expense);

        Jvtran expenseEntry = new Jvtran();
        expenseEntry.setTrno(trno);
        expenseEntry.setAcno(expenseDTO.getExpenseacno()); // Account Number for Expenses
        expenseEntry.setAmount(expenseDTO.getAmount());
        expenseEntry.setId(1); // Set an appropriate ID for expense
        expenseEntry.setNote("Entry of Exp #" + expense.getDocno());
        expenseEntry.setUserid(expenseDTO.getUserid());
        expenseEntry.setBrhid(expenseDTO.getBrhid());
        expenseEntry.setDate(expenseDTO.getDate());
        expenseEntry.setDocNo(expense.getDocno());
        expenseEntry.setDtype("EXP");
        expenseEntry.setOutAmount(0.0);
        jvrepo.save(expenseEntry);

        Jvtran vendorEntry = new Jvtran();
        vendorEntry.setTrno(trno);
        vendorEntry.setAcno(expenseDTO.getVendorid()); // Account Number for Vendors
        vendorEntry.setAmount(expenseDTO.getAmount()*-1); // Negate the amount
        vendorEntry.setId(-1); // Set an appropriate ID for vendor entry
        vendorEntry.setNote("Vendor entry of Exp #" + expense.getDocno());
        vendorEntry.setUserid(expenseDTO.getUserid());
        vendorEntry.setBrhid(expenseDTO.getBrhid());
        vendorEntry.setDate(expenseDTO.getDate());
        vendorEntry.setDocNo(expense.getDocno());
        vendorEntry.setDtype("EXP");
        vendorEntry.setOutAmount(0.0);
        jvrepo.save(vendorEntry);

        return expense;
    }

    @Transactional
    public void deleteExpType(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        repo.deleteById(id);
    }

}
