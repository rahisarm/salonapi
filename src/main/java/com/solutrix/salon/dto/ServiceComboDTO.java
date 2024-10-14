package com.solutrix.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceComboDTO {
    public Integer docno;
    public String refname;
    public Double amount;
    public String servicetype;
}
