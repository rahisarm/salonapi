package com.solutrix.salon.service;

import com.solutrix.salon.entity.InvoiceDetail;
import com.solutrix.salon.entity.InvoiceMaster;
import com.solutrix.salon.repository.InvoiceDetailRepo;
import com.solutrix.salon.repository.InvoiceMasterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceMasterRepo invoiceMasterRepo;

    @Autowired
    private InvoiceDetailRepo invoiceDetailRepo;

    @Transactional
    public InvoiceMaster createInvoiceMaster(InvoiceMaster invoiceMaster) {
        return invoiceMasterRepo.save(invoiceMaster);
    }

    public List<InvoiceMaster> getAllInvoices() {
        return invoiceMasterRepo.findAll();
    }

    public Optional<InvoiceMaster> getInvoiceById(Integer id) {
        return invoiceMasterRepo.findById(id);
    }

    public void deleteInvoice(Integer id) {
        invoiceMasterRepo.deleteById(id);
    }


}
