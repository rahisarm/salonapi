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
@Table(name = "my_head")
public class Account {
    @Id
    @Column(name = "doc_no")
    private int docno;
    private String code;
    @Column(name="acno")
    private String account;
    private String acname;
    private int status;
    private int userid;
    private int brhid;
    private Date date;
    private String actype;
    @Column(name="voc_no")
    private int vocno;
}
