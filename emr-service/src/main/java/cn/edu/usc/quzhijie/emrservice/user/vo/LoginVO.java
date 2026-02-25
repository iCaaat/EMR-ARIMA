package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginVO {
    private Integer userId;
    private String username;
    private String realName;
    private Character gender;
    private String phone;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String token;
    private String roleName;
}
