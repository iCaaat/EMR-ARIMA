package cn.edu.usc.quzhijie.userservice.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class UserBase {
    private Integer userId;
    private String username;
    private String password;
    private String realName;
    private Character gender;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;
}
