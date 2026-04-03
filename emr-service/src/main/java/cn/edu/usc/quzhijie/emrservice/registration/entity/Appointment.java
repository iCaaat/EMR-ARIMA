package cn.edu.usc.quzhijie.emrservice.registration.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Appointment {
    private Integer appointmentId;
    private Integer patientId;
    private Integer doctorId;
    private String doctorName;
    private Integer departmentId;
    private String departmentName;
    private Integer slotId;
    private LocalDate visitDate;
    private String period;
    private Integer queueNumber;
    private Integer fee;
    private String contactPhone;
    private String payeeCode;
    private String status;
    private LocalDateTime createTime;
}
