package cn.edu.usc.quzhijie.emrservice.record.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyRecordVO {
    private Integer recordId;
    private Integer patientId;
    private String patientName;
    private Integer doctorId;
    private String doctorName;
    private String departmentName;
    private Integer appointmentId;

    private String chiefComplaint;
    private String presentIllness;
    private String pastHistory;
    private String allergyHistory;
    private String physicalExam;
    private String auxiliaryExam;
    private String diagnosis;
    private String treatmentPlan;
    private String prescription;
    private String doctorAdvice;
    private String remark;
    private Integer status;

    private String visitDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
