package cn.edu.usc.quzhijie.emrservice.user.dto;

import lombok.Data;

@Data
public class UserChangePasswordDTO {
    private Integer uid;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
