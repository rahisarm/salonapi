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
@Table(name = "my_user")
public class User {
    @Id
    @Column(name = "doc_no")
    private int docno;
    private String username;
    private String fullname;
    private String password;
    private String email;
    private String mobile;
    private int status;
    private int roleid;
    private int userid;
    private int brhid;
    private Date date;
    @Column(name="voc_no")
    private int vocno;



}
