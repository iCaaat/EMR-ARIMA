package cn.edu.usc.quzhijie.emrservice.schedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleUpdateDTO {
    @NotNull(message = "排班ID不能为空")
    private Integer scheduleId;
    private LocalTime amStartTime;
    private LocalTime amEndTime;
    private LocalTime pmStartTime;
    private LocalTime pmEndTime;
    private Integer intervalMinute;
    private Integer status;
    private Integer maxNumber;
}
