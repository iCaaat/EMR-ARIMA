package cn.edu.usc.quzhijie.emrservice.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserChangePasswordDTO {
    @NotNull(message = "用户ID不能为空")
    private Integer uid;
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "新密码长度必须在6-20之间")
    private String newPassword;
    @NotBlank(message = "请确认新密码")
    private String confirmNewPassword;
}
