package cn.edu.usc.quzhijie.userservice.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class UserVO {
    private Integer userId;
    private String username;
    private String realName;
    private Character gender;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;
}
