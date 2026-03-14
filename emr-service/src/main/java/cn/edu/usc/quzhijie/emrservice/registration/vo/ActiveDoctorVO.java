package cn.edu.usc.quzhijie.emrservice.registration.vo;

import lombok.Data;

@Data
public class ActiveDoctorVO {
    private Integer doctorId;
    private Integer departmentId;
    private Integer scheduleId;
    private String realName;
    private String gender;
    private String doctorTitle;
    private String outpatientType;
    private Integer fee;
    private String avatar;
    private Integer maxNumber;
    private Integer remainNumber;
}
