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
@Table(name = "my_trno")
public class Trno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trno")
    private int trno;
    private String trtype;
}
