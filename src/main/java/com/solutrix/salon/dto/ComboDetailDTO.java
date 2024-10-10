package com.solutrix.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboDetailDTO {
    private int docno;
    private int rdocno;
    private int psrno;
    private String refname;
    private double amount;
}
