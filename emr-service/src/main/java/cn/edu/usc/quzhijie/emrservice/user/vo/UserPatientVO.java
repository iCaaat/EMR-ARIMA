package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Data;

@Data
public class UserPatientVO {
    private Integer patientId;
    private Integer belongingUid;
    private String realNameSecret;
    private String idCardSecret;
    private String relation;
    private String relationDisplay;
}
