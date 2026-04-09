package cn.edu.usc.quzhijie.emrservice.user.dto;

import cn.edu.usc.quzhijie.emrservice.common.annotation.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * 用户注册入参
 */
@Data
public class PatientRegisterDTO {
    @UserName
    private String username;

    @Password
    private String password;

    @RealName
    private String realName;

    @IdCard
    private String idCard;

    private String gender;
    private LocalDate birthday;

    @NotBlank
    @Phone
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String roleCode;
}
