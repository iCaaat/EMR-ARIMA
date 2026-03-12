package cn.edu.usc.quzhijie.emrservice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserDTO {
    private Integer uid;

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z_][a-zA-Z0-9_]{2,19}$", message = "用户名必须以字母或下划线开头，长度为3-20个字符，且只能包含字母、数字和下划线")
    private String username;

    @NotBlank(message = "真实姓名不能为空")
    @Pattern(regexp = "(^[\\u4e00-\\u9fa5]{2,18}$)|(^[a-zA-Z\\s]{1,64}$)", message = "真实姓名必须是2-18个汉字或包含空格的64个字母以内")
    private String realName;

    @NotBlank(message = "身份证号码不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|X|x)$", message = "身份证号码格式不正确")
    private String idCard;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号必须是11位数字")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;
}
