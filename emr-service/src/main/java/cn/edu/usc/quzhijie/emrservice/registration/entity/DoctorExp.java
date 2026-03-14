package cn.edu.usc.quzhijie.emrservice.registration.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoctorExp {
    private Integer doctorId;
    private Integer departmentId;
    private Integer uid;
    private String realName;
    private String idCard;
    private String gender;
    private String specialty;
    private String description;
    private String qualification;
    private Integer experienceYears;
    private String doctorTitle;
    private Integer fee;
    private String avatar;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
