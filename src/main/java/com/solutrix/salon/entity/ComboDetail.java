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
@Table(name = "my_combodet")
public class ComboDetail {
    @Id
    @Column(name = "doc_no")
    private int docno;
    @ManyToOne
    @JoinColumn(name = "rdocno")
    @JsonBackReference
    private ComboMaster comboMaster;
    @ManyToOne
    @JoinColumn(name = "psrno")
    private Product product;

}
