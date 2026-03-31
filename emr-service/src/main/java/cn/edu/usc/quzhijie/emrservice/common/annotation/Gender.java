package cn.edu.usc.quzhijie.emrservice.common.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@NotBlank
@Pattern(regexp = "^[MFU]$", message = "性别必须是'M'、'F'或'U'")
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Gender {
    String message() default "性别格式不正确，必须是'M'、'F'或'U'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
