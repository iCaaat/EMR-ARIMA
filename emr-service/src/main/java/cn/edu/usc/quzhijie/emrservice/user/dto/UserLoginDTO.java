package cn.edu.usc.quzhijie.emrservice.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录入参
 */
@Data
public class UserLoginDTO {
    @NotBlank(message = "请输入用户名")
    private String username;
    private String password;
}
