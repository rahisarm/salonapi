package com.solutrix.salon.service;

import com.solutrix.salon.entity.Account;
import com.solutrix.salon.entity.Employee;
import com.solutrix.salon.exception.ResourceNotFoundException;
import com.solutrix.salon.repository.AccountRepo;
import com.solutrix.salon.repository.EmployeeRepo;
import com.solutrix.salon.repository.EmployeeRepo;
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
public class EmployeeService {

    @Autowired
    private EmployeeRepo repo;

    @Autowired
    private AccountRepo accountRepo;


    @Autowired
    private AccountService accountService;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Employee> getAllEmployees(int brhid) {
        return repo.findAllByBrhidIsAndAndStatusNot(brhid,7);
    }


    public Optional<Employee> getEmployeeById(int docno) {
        return repo.findById(docno);
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        entityManager.clear();
        employee.setStatus(3);
        Optional<Integer> maxDocNo = repo.findMaxDocNo();
        Optional<Integer> maxVocNo = repo.findMaxVocNo(employee.getBrhid());
        employee.setDocno(maxDocNo.orElse(0) + 1);
        employee.setDate(Date.valueOf(LocalDate.now()));
        employee.setVocno(maxVocNo.orElse(0) + 1);

        Account account = new Account();
        account.setCode("HR-" + employee.getDocno());
        account.setAccount(employee.getDocno()+"");
        account.setActype("HR");
        account.setBrhid(employee.getBrhid());
        account.setAcname(employee.getRefname());
        account.setUserid(employee.getUserid());

        account=accountService.createAccount(account);
        employee.setAcno(account.getDocno());
        return repo.save(employee);
    }

    @Transactional
    public Employee updateEmployee(int id, Employee employee) {
        Employee employeeitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));
        employeeitem.setEmail(employee.getEmail());
        employeeitem.setMobile(employee.getMobile());
        employeeitem.setRefname(employee.getRefname());
        employeeitem.setActive(employee.isActive());
        employeeitem.setTargetamt(employee.getTargetamt());

        Account account=accountRepo.findById(employeeitem.getAcno()).orElseThrow(()-> new ResourceNotFoundException("Account Not Found"));

        account.setCode("HR-" + employeeitem.getDocno());
        account.setAccount(employeeitem.getDocno()+"");
        account.setActype("HR");
        account.setBrhid(employeeitem.getBrhid());
        account.setAcname(employeeitem.getRefname());
        account.setUserid(employeeitem.getUserid());

        account=accountService.updateAccount(account.getDocno(),account);
        return repo.save(employeeitem);
    }

    @Transactional
    public void deleteEmployee(int id) {
        /*User useritem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        useritem.setStatus(7);*/
        Employee employeeitem=repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Not Found"));
        Account account=accountRepo.findById(employeeitem.getAcno()).orElseThrow(()-> new ResourceNotFoundException("Account Not Found"));
        accountService.deleteAccount(account.getDocno());
        repo.deleteById(id);
    }

}
