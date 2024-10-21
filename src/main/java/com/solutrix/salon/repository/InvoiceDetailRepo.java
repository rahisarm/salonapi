package com.solutrix.salon.repository;


import com.solutrix.salon.entity.InvoiceDetail;
import com.solutrix.salon.entity.InvoiceMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InvoiceDetailRepo extends JpaRepository<InvoiceDetail,Integer> {

    void deleteAllByInvoiceMaster(InvoiceMaster invoiceMaster);
}
