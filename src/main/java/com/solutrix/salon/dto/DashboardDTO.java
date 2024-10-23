package com.solutrix.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    private Double totalInvoice;
    private Double totalExpense;
    private Integer newCustomers;
    private Integer activeEmployees;
    private Integer brhid;
    private Integer userid;
    private Date fromdate;
    private Date todate;
    private Integer reportbrhid;
}
