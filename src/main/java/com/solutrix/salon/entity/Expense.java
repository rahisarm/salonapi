package com.solutrix.salon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_expense")
public class Expense {
    @Id
    @Column(name = "doc_no")
    private int docno;
    private int exptype;
    private int paytype;
    private String paytypeno;
    private String billno;
    private int vendorid;
    private double amount = 0.0;
    @Column(name = "vat")
    private double tax = 0.0;
    @Column(name = "total")
    private double nettotal = 0.0;
    private String remarks;
    private int status;
    private int userid;
    private int brhid;
    private Date date;
    private int trno;
    private int expenseacno;
    @Column(name="voc_no")
    private int vocno;
}
