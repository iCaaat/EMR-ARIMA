package cn.edu.usc.quzhijie.emrservice.registration.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SelectScheduleVO {
    private String avatar;
    private String realName;
    private String doctorTitle;
    private String departmentName;
    private LocalDate appointmentDate;
    private Integer fee;
    private String specialty;
    private String description;
}
