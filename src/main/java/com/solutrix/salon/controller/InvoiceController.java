package com.solutrix.salon.controller;

import com.solutrix.salon.entity.InvoiceMaster;
import com.solutrix.salon.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceMaster> createInvoice(@RequestBody InvoiceMaster invoice) {
        return ResponseEntity.ok(invoiceService.createInvoiceMaster(invoice));
    }

}
