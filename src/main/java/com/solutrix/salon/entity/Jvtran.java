package com.solutrix.salon.entity;

import jakarta.persistence.*;
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
@Table(name = "my_jvtran")
public class Jvtran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rowno;
    private int trno;
    private int acno;
    private double amount;
    private int id;
    private String note;
    private int userid;
    private int brhid;
    private Date date;
    private int docNo;
    private String dtype;
    private double outAmount;
}
