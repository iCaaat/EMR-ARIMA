package cn.edu.usc.quzhijie.emrservice.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@NotBlank(message = "用户名不能为空")
@Pattern(
        regexp = "^[a-zA-Z_][a-zA-Z0-9_]{2,19}$",
        message = "用户名必须以字母或下划线开头，长度为3-20个字符，且只能包含字母、数字和下划线"
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserName {
    String message() default "用户名必须以字母或下划线开头，长度为3-20个字符，且只能包含字母、数字和下划线";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
