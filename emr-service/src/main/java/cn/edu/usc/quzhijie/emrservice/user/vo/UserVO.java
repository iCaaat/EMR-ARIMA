package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    private Integer uid;
    private String username;
    private String realName;
    private String idCard;
    private Character gender;
    private Date birthday;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;

    private Integer roleId;
    private String roleName;
    private String roleCode;
    private String description;
}
