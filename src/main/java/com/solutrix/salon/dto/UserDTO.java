package com.solutrix.salon.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private int docno;
    private String username;
    private String fullname;
    private String password;
    private String email;
    private String mobile;
    private int status;
    private int roleid;
    private int userid;
    private int brhid;
    private Date date;
    private int vocno;
    private String userlevel;

    public UserDTO(int docno, String username, String fullname, String email, String mobile, int roleid, String userlevel) {
        this.docno = docno;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.mobile = mobile;
        this.roleid = roleid;
        this.userlevel = userlevel;
    }
}
