package com.solutrix.salon.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_combomaster")
public class ComboMaster {
    @Id
    @Column(name = "doc_no")
    private int docno;
    private String refname;
    private Date fromdate;
    private Date todate;
    private double amount;
    private String description;
    private int status;
    private int userid;
    private int brhid;
    private Date date;
    @Column(name="voc_no")
    private int vocno;

    @OneToMany(mappedBy = "comboMaster", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ComboDetail> comboDetailList;
}
