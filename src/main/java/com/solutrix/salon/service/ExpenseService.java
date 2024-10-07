package com.solutrix.salon.service;

import com.solutrix.salon.component.Common;
import com.solutrix.salon.dto.ExpenseDTO;
import com.solutrix.salon.entity.*;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo repo;

    @Autowired
    private JvtranRepo jvrepo;

    @Autowired
    private TrnoService trnoService;

    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    private PayTypeRepo payTypeRepo;

    @Autowired
    private ExpTypeRepo expTypeRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private Common objcommon;

    @PersistenceContext
    private EntityManager entityManager;

    public List<ExpenseDTO> getAllExpenses(int brhid) {
        List<Expense> expenseList=repo.findAllByBrhidIs(brhid);
        List<ExpenseDTO> expenseDTOList=new ArrayList<ExpenseDTO>();

        expenseList.forEach(exp->{
            ExpenseDTO expenseDTO=new ExpenseDTO();
            expenseDTO.setAccount(exp.getExpenseacno());
            expenseDTO.setAmount(exp.getAmount());
            expenseDTO.setDate(exp.getDate());
            expenseDTO.setBillno(exp.getBillno());
            expenseDTO.setTax(exp.getTax());
            expenseDTO.setNettotal(exp.getNettotal());
            expenseDTO.setDocno(exp.getDocno());
            expenseDTO.setExptype(exp.getExptype());
            expenseDTO.setPaytype(exp.getPaytype());
            expenseDTO.setPaytypeno(exp.getPaytypeno());
            expenseDTO.setRemarks(exp.getRemarks());
            expenseDTO.setVendor(exp.getVendorid());
            expenseDTO.setVocno(exp.getVocno());
            Optional<Vendor> vendor=vendorRepo.findById(exp.getVendorid());
            if(vendor.isPresent()) {
                expenseDTO.setVendorname(vendor.get().getRefname());
            }
            Optional<PayType> payType=payTypeRepo.findById(exp.getPaytype());
            if(payType.isPresent()) {
                expenseDTO.setPaytypename(payType.get().getRefname());
            }
            Optional<ExpType> expType=expTypeRepo.findById(exp.getExptype());
            if(expType.isPresent()) {
                expenseDTO.setExptypename(expType.get().getRefname());
            }
            Optional<Account> account=accountRepo.findById(exp.getExpenseacno());
            if(account.isPresent()) {
                expenseDTO.setAccountname(account.get().getAcname());
            }
            expenseDTOList.add(expenseDTO);
        });
        return expenseDTOList;
    }

    @Transactional
    public Expense createExpense(ExpenseDTO expenseDTO) {
        entityManager.clear();

        int trno= trnoService.getNewTrno("EXP");
        System.out.println(expenseDTO);
        System.out.println("Date Recieved"+expenseDTO.getDate());
        Expense expense = new Expense();
        expense.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(expense.getBrhid());
        expense.setDocno(maxDocNo.orElse(0) + 1);
        expense.setDate(expenseDTO.getDate());
        expense.setVocno(maxVocNo.orElse(0) + 1);
        expense.setTrno(trno);  // Assign generated trno

        expense.setExptype(expenseDTO.getExptype());
        expense.setPaytype(expenseDTO.getPaytype());
        expense.setPaytypeno(expenseDTO.getPaytypeno());
        expense.setBillno(expenseDTO.getBillno());
        expense.setVendorid(expenseDTO.getVendor());
        expense.setAmount(expenseDTO.getAmount());
        expense.setTax(expenseDTO.getTax());
        expense.setNettotal(expenseDTO.getNettotal());
        expense.setRemarks(expenseDTO.getRemarks());
        expense.setUserid(expenseDTO.getUserid());
        expense.setBrhid(expenseDTO.getBrhid());
        expense.setDate(expenseDTO.getDate());
        expense.setExpenseacno(expenseDTO.getAccount());

        repo.save(expense);

        Jvtran expenseEntry = new Jvtran();

        expenseEntry.setTrno(trno);
        expenseEntry.setAcno(expenseDTO.getAccount()); // Account Number for Expenses
        expenseEntry.setAmount(expenseDTO.getNettotal());
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

        int vendorEntryAcno=Integer.parseInt(objcommon.getAccountNumberByPayType(expenseDTO.getPaytype(),expenseDTO.getVendor()));
        vendorEntry.setTrno(trno);
        vendorEntry.setAcno(vendorEntryAcno); // Account Number for Vendors
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

        if(expenseDTO.getTax()>0.0){
            Jvtran taxEntry = new Jvtran();
            taxEntry.setTrno(trno);
            taxEntry.setAcno(objcommon.getTaxAcno()); // Account Number for Vendors
            taxEntry.setAmount(expenseDTO.getTax()*-1); // Negate the amount
            taxEntry.setId(-1); // Set an appropriate ID for vendor entry
            taxEntry.setNote("Vendor Tax entry of Exp #" + expense.getDocno());
            taxEntry.setUserid(expenseDTO.getUserid());
            taxEntry.setBrhid(expenseDTO.getBrhid());
            taxEntry.setDate(expenseDTO.getDate());
            taxEntry.setDocNo(expense.getDocno());
            taxEntry.setDtype("EXP");
            taxEntry.setOutAmount(0.0);
            jvrepo.save(taxEntry);
        }




        return expense;
    }

    @Transactional
    public void deleteExpType(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        repo.deleteById(id);
    }

}
