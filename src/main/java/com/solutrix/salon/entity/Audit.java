package com.solutrix.salon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "datalog")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowno")
    private Integer srno;
    private Integer docno;
    private String dtype;
    @Column(name = "entry")
    private String mode;
    private Integer userid;
    private Integer brhid;
}
