package cn.edu.usc.quzhijie.emrservice.common.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class DoctorSchedule {
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
    private LocalDateTime createTime;
}
