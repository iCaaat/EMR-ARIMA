package cn.edu.usc.quzhijie.emrservice.registration.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentFilterDTO {
    private Integer departmentId;
    private String doctorName;
    private String contactPhone;
    private LocalDate visitDate;
    private Integer status;
}
