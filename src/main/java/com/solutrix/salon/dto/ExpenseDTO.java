package com.solutrix.salon.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private int docno;
    private int exptype;
    private int paytype;
    private String paytypeno;
    private String billno;
    private int vendorid;
    private double amount = 0.0;
    private double tax = 0.0;
    private double nettotal = 0.0;
    private String remarks;
    private int status;
    private int userid;
    private int brhid;
    private Date date;
    private int trno;
    private int expenseacno;
    private int vocno;
}
