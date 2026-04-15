package cn.edu.usc.quzhijie.emrservice.user.dto;

import cn.edu.usc.quzhijie.emrservice.common.annotation.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorRegisterDTO {
    private Integer doctorId;
    @UserName
    private String username;
    @Password
    private String password;
    @RealName
    private String realName;
    @IdCard
    private String idCard;
    @Phone
    @NotBlank
    private String phone;
    @Email
    @NotBlank
    private String email;
    @NotNull
    private Integer departmentId;
    private String gender;
    private LocalDate birthday;

    @NotBlank(message = "专业领域不能为空")
    private String specialty;

    private String description;

    @NotBlank
    private String qualification;

    @Min(value = 0, message = "工作经验必须大于或等于0")
    @Max(value = 70, message = "工作经验必须小于或等于70")
    private Integer experienceYears;

    @NotBlank
    private String doctorTitle;

    private String outpatientType;

    @Min(value = 0, message = "挂号费必须大于或等于0")
    @Max(value = 200, message = "挂号费必须小于或等于200")
    private Integer fee;

    private String avatarUrl;
    private String roleCode;
}
