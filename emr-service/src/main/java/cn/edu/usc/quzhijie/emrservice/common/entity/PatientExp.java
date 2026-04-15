package cn.edu.usc.quzhijie.emrservice.common.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PatientExp {
    private Integer patientId;
    private Integer belongingUid;
    private String realName;
    private String idCard;
    private String gender;
    private LocalDate birthday;
    private String contactPhone;
    private String address;
    private String nationality;
    private String occupation;
    private Integer maritalStatus;
    private String emergencyPhone;
    private String emergencyContact;
    private String insuranceNumber;
    private String medicalHistory;
    private String allergies;
    private String relation;
    private String relationDisplay;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;
}
