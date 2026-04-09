package cn.edu.usc.quzhijie.emrservice.registration.vo;

import lombok.Data;

@Data
public class UserAppointmentVO {
    private Integer appointmentId;
    private Integer patientId;
    private String patientName;
    private Integer doctorId;
    private String doctorName;
    private String departmentName;
    private String visitDate;
    private String period;
    private String displayPeriod;
    private String displayNo;
    private String contactPhone;
    private Integer fee;
    private String payeeCode;
    private Integer status;
    private String displayStatus;
}
