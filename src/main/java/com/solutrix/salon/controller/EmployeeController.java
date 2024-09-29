package com.solutrix.salon.controller;

import com.solutrix.salon.entity.Employee;
import com.solutrix.salon.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/all/{brhid}")
    public List<Employee> getAllEmployees(@PathVariable int brhid) {
        return service.getAllEmployees(brhid);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.createEmployee(employee);
    }

    @GetMapping("/{docno}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int docno) {
        Optional<Employee> employee = service.getEmployeeById(docno);
        return employee.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{docno}")
    public ResponseEntity deleteEmployee(@PathVariable int docno) {
        try {
            service.deleteEmployee(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee updatedEmployee) {
        try {
            Employee updated = service.updateEmployee(updatedEmployee.getDocno(), updatedEmployee);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
