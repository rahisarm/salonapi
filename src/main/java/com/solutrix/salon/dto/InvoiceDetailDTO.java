package com.solutrix.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailDTO {
    private Integer serviceid;
    private String servicetype;
    private String servicename;
    private Double amount;

}
