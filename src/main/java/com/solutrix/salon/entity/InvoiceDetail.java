package com.solutrix.salon.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "my_invd")
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rowno;
    @ManyToOne()
    @JoinColumn(name = "rdocno")
    @JsonBackReference
    private InvoiceMaster invoiceMaster;
    private Integer serviceid;
    private String servicetype;
    private Double amount;
    private Integer trno;
}
