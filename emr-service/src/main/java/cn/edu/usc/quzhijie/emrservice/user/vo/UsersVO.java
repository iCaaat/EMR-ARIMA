package cn.edu.usc.quzhijie.emrservice.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsersVO {
    private Integer uid;
    private String username;
    private String phone;
    private String email;
    private String realName;
    private String idCard;
    private String realNameSecret;
    private String idCardSecret;
    private String roleName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
