package cn.edu.usc.quzhijie.emrservice.common.entity;

import lombok.Data;

@Data
public class MedicalRecord {
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
}
