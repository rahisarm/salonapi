package com.solutrix.salon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    private int mno;
    private String menuname;
    private String menupath;
    private String printpath;
    private int pmenu;
    private String doctype;
    private String gate;
}
