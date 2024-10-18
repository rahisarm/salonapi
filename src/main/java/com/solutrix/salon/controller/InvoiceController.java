package com.solutrix.salon.controller;

import com.solutrix.salon.dto.InvoiceMasterDTO;
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

    @DeleteMapping("/{docno}")
    public ResponseEntity deleteInvoice(@PathVariable int docno) {
        try {
            invoiceService.deleteInvoice(docno);
            return ResponseEntity.ok(true);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
