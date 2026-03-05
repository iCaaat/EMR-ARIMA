package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserVO {
    private Integer uid;
    private String username;
    private String realName;
    private String idCard;
    private String gender;
    private LocalDate birthday;
    private String phone;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private Integer roleId;
    private String roleName;
    private String roleCode;
    private String description;
}
