package com.solutrix.salon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "my_userlevel")
public class Userlevel {
    @Id
    @Column(name = "doc_no")
    private int docno;
    private String userlevel;
    private int status;

}
