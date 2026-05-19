package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientOverviewVO {
    private Integer patientCount;
    private PatientBriefVO defaultPatient;
    private AppointmentBriefVO nextAppointment;
    private Integer totalVisitCount;
    private MedicalRecordBriefVO latestMedicalRecord;
    private Integer medicalRecordCount;
    private List<AppointmentBriefVO> recentAppointments;
    private HospitalIntroVO hospitalIntro;

    @Data
    public static class PatientBriefVO {
        private Integer patientId;
        private String realName;
        private String relationDisplay;
    }
    @Data
    public static class AppointmentBriefVO {
        private Integer appointmentId;
        private Integer patientId;
        private String patientName;

        private Integer doctorId;
        private String doctorName;

        private Integer departmentId;
        private String departmentName;

        private LocalDate visitDate;
        private String period;

        private Integer queueNumber;
        private String displayNo;

        private Integer fee;

        private Integer status;
    }

    @Data
    public static class MedicalRecordBriefVO {
        private Integer recordId;
        private Integer patientId;
        private String patientName;

        private String doctorName;
        private String departmentName;

        private String diagnosis;
        private String doctorAdvice;

        private LocalDate visitDate;
        private Integer status;
    }

    @Data
    public static class HospitalIntroVO {
        private String title;
        private String description;
        private String imageUrl;
    }

}
