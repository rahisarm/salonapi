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
    private String exptypename;
    private int paytype;
    private String paytypename;
    private String paytypeno;
    private String billno;
    private int vendor;
    private String vendorname;

    private double amount = 0.0;
    private double tax = 0.0;
    private double nettotal = 0.0;
    private String remarks;
    private int status;
    private int userid;
    private int brhid;
    private Date date;
    private int trno;
    private int account;
    private String accountname;

    private int vocno;
}
