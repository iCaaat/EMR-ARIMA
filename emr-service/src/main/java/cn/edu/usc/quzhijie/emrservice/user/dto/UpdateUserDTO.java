package cn.edu.usc.quzhijie.emrservice.user.dto;

import cn.edu.usc.quzhijie.emrservice.common.annotation.IdCard;
import cn.edu.usc.quzhijie.emrservice.common.annotation.Phone;
import cn.edu.usc.quzhijie.emrservice.common.annotation.RealName;
import cn.edu.usc.quzhijie.emrservice.common.annotation.UserName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserDTO {
    private Integer uid;

    @UserName
    private String username;

    @RealName
    private String realName;

    @IdCard
    private String idCard;

    @NotBlank
    @Phone
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;
}
