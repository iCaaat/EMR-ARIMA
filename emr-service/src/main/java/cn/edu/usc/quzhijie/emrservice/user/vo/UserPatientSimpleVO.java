package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Data;

@Data
public class UserPatientSimpleVO {
    private Integer patientId;
    private String realName;
    private String idCardSecret;
}
