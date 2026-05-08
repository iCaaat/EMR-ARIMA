package cn.edu.usc.quzhijie.emrservice.record.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostRecordDTO {
    private Integer recordId;
    private Integer appointmentId;
    private Integer doctorId;
    private String doctorName;
    private Integer patientId;
    private String patientName;
    private String departmentName;

    @NotBlank(message = "主诉不能为空")
    private String chiefComplaint;
    private String presentIllness;
    private String pastHistory;
    private String allergyHistory;
    private String physicalExam;
    private String auxiliaryExam;
    @NotBlank(message = "诊断不能为空")
    private String diagnosis;
    private String treatmentPlan;
    private String prescription;
    private String doctorAdvice;
    private String remark;

    private LocalDate visitDate;
    private Integer status;
}
