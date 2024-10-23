package com.solutrix.salon.service;

import com.solutrix.salon.dto.DashboardDTO;
import com.solutrix.salon.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {


    @Autowired
    private InvoiceMasterRepo invoiceMasterRepo;

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private EmployeeRepo employeeRepo;


    public DashboardDTO getDashboardData(DashboardDTO dto) {
        DashboardDTO dashboardDTO = new DashboardDTO();

        Double totalinvoice= invoiceMasterRepo.findSumSubtotal(dto.getFromdate(),dto.getTodate(),dto.getReportbrhid());
        dashboardDTO.setTotalInvoice(totalinvoice);

        return dashboardDTO;
    }
}
