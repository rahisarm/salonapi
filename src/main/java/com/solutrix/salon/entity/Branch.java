package com.solutrix.salon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "my_brch")
public class Branch {
    @Id
    @Column(name = "doc_no")
    private int docno;

    @Column(name="branchname")
    private String branchname;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private int status;

    @Column(name = "date")
    private Date date;
}
