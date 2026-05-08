package cn.edu.usc.quzhijie.emrservice.registration.dto;

import cn.edu.usc.quzhijie.emrservice.common.query.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorAppointmentFilterDTO extends PageQuery {
    private String patientName;
    private String contactPhone;
    private LocalDate visitDate;
    private Integer status;
}
