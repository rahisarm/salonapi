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
@Table(name = "my_acbook")
public class Client {
    @Id
    @Column(name = "cldocno")
    private Integer docno;
    private String refname;
    private String email;
    private String mobile;
    private Integer status;
    private Integer userid;
    private Integer brhid;
    private Date date;
    @Column(name="voc_no")
    private Integer vocno;
    private Integer acno;
}
