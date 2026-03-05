package cn.edu.usc.quzhijie.emrservice.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录入参
 */
@Data
public class UserLoginDTO {
    @NotBlank(message = "用户名或密码不能为空")
    private String username;
    @NotBlank(message = "用户名或密码不能为空")
    private String password;
}
