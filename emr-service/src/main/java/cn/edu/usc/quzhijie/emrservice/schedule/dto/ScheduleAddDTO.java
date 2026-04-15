package cn.edu.usc.quzhijie.emrservice.schedule.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ScheduleAddDTO {
    @NotNull(message = "医生ID列表不能为空")
    private List<Integer> doctorIds;
    @NotBlank(message = "排班规则类型不能为空")
    private String ruleType;

    @NotNull(message = "上午开始时间不能为空")
    private LocalTime amStartTime;
    @NotNull(message = "上午结束时间不能为空")
    private LocalTime amEndTime;
    @NotNull(message = "下午开始时间不能为空")
    private LocalTime pmStartTime;
    @NotNull(message = "下午结束时间不能为空")
    private LocalTime pmEndTime;

    @NotNull(message = "看诊间隔不能为空")
    @Max(value = 40, message = "看诊间隔必须小于或等于40分钟")
    @Min(value = 1, message = "看诊间隔必须大于或等于1分钟")
    private Integer intervalMinute;

    private LocalDate startDate;
    private LocalDate endDate;
    private List<Integer> weekdays;

    private LocalDate singleDate;

    private List<LocalDate> multiDates;
}
