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
@NotBlank(message = "真实姓名不能为空")
@Pattern(
        regexp = "(^[\\u4e00-\\u9fa5]{2,18}$)|(^[a-zA-Z\\s]{1,64}$)",
        message = "真实姓名必须是2-18个汉字或64字母以内"
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RealName {
    String message() default "真实姓名格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
