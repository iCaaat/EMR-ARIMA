package cn.edu.usc.quzhijie.emrservice.user.dto;

import cn.edu.usc.quzhijie.emrservice.common.annotation.IdCard;
import cn.edu.usc.quzhijie.emrservice.common.annotation.Phone;
import cn.edu.usc.quzhijie.emrservice.common.annotation.RealName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDetailDTO {
    private Integer patientId;
    @RealName
    private String realName;
    @IdCard
    private String idCard;
    @NotBlank
    @Phone
    private String contactPhone;
    private String gender;
    private LocalDate birthday;
    private String address;
    private String nationality;
    private String occupation;
    private Integer maritalStatus;
    private String emergencyPhone;
    private String emergencyContact;
    private String insuranceNumber;
    private String relation;
    private String relationDisplay;
}
