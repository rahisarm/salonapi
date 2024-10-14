package com.solutrix.salon.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer docno;
    private String refname;
    private Double amount;
    private Integer status;
    private Integer userid;
    private Integer brhid;
    private Date date;
    private Integer vocno;
    private String servicetype="Service";
}
