package cn.edu.usc.quzhijie.userservice.dto;

import lombok.Data;

/**
 * 用户注册入参
 */
@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String realName;
    private Character gender;
    private String phone;
    private String email;
    private String roleCode;
}
