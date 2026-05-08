package cn.edu.usc.quzhijie.emrservice.schedule.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DoctorScheduleVO {
    private Integer scheduleId;
    private Integer doctorId;
    private Integer departmentId;
    private LocalDate workDate;
    private LocalTime amStartTime;
    private LocalTime amEndTime;
    private LocalTime pmStartTime;
    private LocalTime pmEndTime;
    private Integer intervalMinute;
    private Integer maxNumber;
    private Integer status;
}
