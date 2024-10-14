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
@Table(name = "my_invd")
public class InvoiceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rowno;
    @ManyToOne()
    @JoinColumn(name = "rdocno")
    private InvoiceMaster invoiceMaster;
    private Integer serviceid;
    private Double amount;
    private Integer trno;
}
