package cn.edu.usc.quzhijie.emrservice.registration.dto;

import cn.edu.usc.quzhijie.emrservice.common.annotation.Phone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDTO {
    @NotNull
    private Integer patientId;
    @NotNull
    private Integer scheduleId;
    private Integer doctorId;
    private String doctorName;
    private Integer departmentId;
    private String departmentName;
    @NotNull
    private Integer slotId;
    @NotBlank(message = "联系电话不能为空")
    @Phone
    private String contactPhone;
    private String payeeCode;
    private String period;
    private LocalDate visitDate;
    private Integer queueNumber;
    private Integer fee;
}
