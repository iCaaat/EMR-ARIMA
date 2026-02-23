package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class LoginVO {
    private Integer userId;
    private String username;
    private String realName;
    private Character gender;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;
    private String token;
}
