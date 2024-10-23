package com.solutrix.salon.controller;

import com.solutrix.salon.dto.InvoiceMasterDTO;
import com.solutrix.salon.entity.Expense;
import com.solutrix.salon.entity.InvoiceMaster;
import com.solutrix.salon.service.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public InvoiceMaster createInvoice(@RequestBody InvoiceMasterDTO invoice) {
        System.out.println(invoice);
        return invoiceService.createInvoiceMaster(invoice);
    }

    @GetMapping("/all/{brhid}")
    public List<InvoiceMasterDTO> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @PutMapping
    public ResponseEntity<InvoiceMaster> updateInvoice(@RequestBody InvoiceMasterDTO invoice) {
        try {
            System.out.println(invoice);
            InvoiceMaster updated = invoiceService.updateInvoice(invoice);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{docno}")
    public ResponseEntity deleteInvoice(@PathVariable int docno,@RequestParam String userid,@RequestParam String brhid) {
        try {
            invoiceService.deleteInvoice(docno,userid,brhid);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
