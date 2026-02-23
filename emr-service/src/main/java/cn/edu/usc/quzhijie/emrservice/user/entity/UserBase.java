package cn.edu.usc.quzhijie.emrservice.user.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class UserBase {
    private Integer uid;
    private String username;
    private String password;
    private String realName;
    private String idCard;
    private Character gender;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;
}
