package com.solutrix.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboMasterDTO {
    private int docno;
    private String refname;
    private Date fromdate;
    private Date todate;
    private double amount;
    private String description;
    private int status;
    private int userid;
    private int brhid;
    private Date date;
    private int vocno;

    private List<ComboDetailDTO> comboDetailList;

}

