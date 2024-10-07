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
    private Integer docno;
    private Integer exptype;
    private Integer paytype;
    private String paytypeno;
    private String billno;
    private Integer vendorid;
    private Double amount = 0.0;
    @Column(name = "vat")
    private Double tax = 0.0;
    @Column(name = "total")
    private Double nettotal = 0.0;
    private String remarks;
    private Integer status;
    private Integer userid;
    private Integer brhid;
    private Date date;
    private Integer trno;
    private Integer expenseacno;
    @Column(name="voc_no")
    private Integer vocno;
}
