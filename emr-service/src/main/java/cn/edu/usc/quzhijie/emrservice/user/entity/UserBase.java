package cn.edu.usc.quzhijie.emrservice.user.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserBase {
    private Integer uid;
    private String username;
    private String password;
    private String realName;
    private String idCard;
    private String phone;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
