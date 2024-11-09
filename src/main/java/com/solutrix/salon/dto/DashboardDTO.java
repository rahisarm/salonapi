package com.solutrix.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Map;

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
    private java.sql.Date fromdate;
    private java.sql.Date todate;
    private java.sql.Date payrolldate;

    private Integer reportbrhid;
    private List<Map<String, Object>> incomeexpensechart;
    private List<Map<String, Object>> expTypeChart;
    private List<Map<String, Object>> expAccountChart;
    private List<Map<String,Object>> payrollList;

    private java.sql.Date dailydate;
    private Double dailyinvcash=0.0;
    private Double dailyinvcard=0.0;
    private Double dailyinvcredit=0.0;
    private Double dailyinvtotal=0.0;

    private Double dailyexpcash=0.0;
    private Double dailyexpcard=0.0;
    private Double dailyexpcredit=0.0;
    private Double dailyexptotal=0.0;

}
