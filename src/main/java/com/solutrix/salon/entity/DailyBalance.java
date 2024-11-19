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
@Table(name = "my_dailybal")
public class DailyBalance {
    @Id
    @Column(name = "doc_no")
    private Integer docno;
    private Double openbalance;
    private Double dailyinvoice;
    private Double dailyexpense;
    private Double dailybalance;
    private Double closingbalance;
    private int userid;
    private int brhid;
    private Date date;
}
