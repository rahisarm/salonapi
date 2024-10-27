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
    private Integer reportbrhid;
    private List<Map<String, Object>> incomeexpensechart;
    private List<Map<String, Object>> expTypeChart;
    private List<Map<String, Object>> expAccountChart;

    private java.sql.Date dailydate;
    private Double dailyinvcash;
    private Double dailyinvcard;
    private Double dailyinvcredit;
    private Double dailyinvtotal;

    private Double dailyexpcash;
    private Double dailyexpcard;
    private Double dailyexpcredit;
    private Double dailyexptotal;

}
