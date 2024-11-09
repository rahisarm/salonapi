package com.solutrix.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigDTO {
    private int docno;
    private String fieldname;
    private int method;
    private String configvalue;
    private String description;
    private String uitype;
}
