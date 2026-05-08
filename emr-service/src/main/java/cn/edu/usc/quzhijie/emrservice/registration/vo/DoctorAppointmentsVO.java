package cn.edu.usc.quzhijie.emrservice.registration.vo;

import lombok.Data;

@Data
public class DoctorAppointmentsVO {
    private Integer appointmentId;
    private Integer patientId;
    private String patientName;
    private String contactPhone;
    private String departmentName;

    private String visitDate;
    private String period;
    private String displayNo;

    private Integer status;
}
