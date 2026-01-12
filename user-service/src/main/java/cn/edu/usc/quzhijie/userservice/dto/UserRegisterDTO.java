package cn.edu.usc.quzhijie.userservice.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private String realName;
    private Character gender;
    private String phone;
    private String email;
}
