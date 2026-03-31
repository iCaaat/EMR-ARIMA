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
@NotBlank(message = "身份证不能为空")
@Pattern(
        regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}" +
                "(0[1-9]|1[0-2])" +
                "(0[1-9]|[12]\\d|3[01])" +
                "\\d{3}(\\d|X|x)$",
        message = "身份证格式不正确"
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdCard {
    String message() default "身份证格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
