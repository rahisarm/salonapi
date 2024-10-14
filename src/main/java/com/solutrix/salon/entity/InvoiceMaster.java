package com.solutrix.salon.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_invm")
public class InvoiceMaster {
    @Id
    @Column(name = "doc_no")
    private int docno;
    private String code;
    private String cldocno;
    private Double amount;
    private Double discount;
    private Double subtotal;
    private Double taxpercent;
    private Double taxamount;
    private Double taxtotal;
    private Double roundoff;
    private Double workbonus;
    private Double nightbonus;
    private Integer trno;
    private String remarks;
    private Double out_amount;

    private int status;
    private int userid;
    private int brhid;
    private Date date;
    @Column(name="voc_no")
    private int vocno;


    @OneToMany(mappedBy = "invoiceMaster", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InvoiceDetail> details=new ArrayList<>();
}
