package com.solutrix.salon.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceMasterDTO {
    private Integer docno;
    private Integer cldocno;
    private Boolean chkworkbonus;
    private Boolean chknightbonus;
    private Date date;
    private String description;
    private Double discount;
    private Double total;
    private Double tax;
    private Double taxpercent;
    private Double nettotal;
    private Integer empid;
    private Integer paytype;

    private Integer status;
    private Integer userid;
    private Integer brhid;
    private Integer vocno;

    private List<InvoiceDetailDTO> details;

    private String paytypename;
    private String clientmobile;
    private String empname;
}
