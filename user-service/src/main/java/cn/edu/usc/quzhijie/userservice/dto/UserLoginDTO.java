package cn.edu.usc.quzhijie.userservice.dto;

import lombok.Data;

/**
 * 用户登录入参
 */
@Data
public class UserLoginDTO {
    private String username;
    private String password;
}
