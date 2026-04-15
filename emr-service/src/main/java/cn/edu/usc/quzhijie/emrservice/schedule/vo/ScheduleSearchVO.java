package cn.edu.usc.quzhijie.emrservice.schedule.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleSearchVO {
    private Integer scheduleId;
    private String doctorName;
    private String departmentName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate workDate;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private LocalTime amStartTime;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private LocalTime amEndTime;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private LocalTime pmStartTime;
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
    private LocalTime pmEndTime;
    private Integer intervalMinute;
    private Integer maxNumber;
    private Integer status;
}
