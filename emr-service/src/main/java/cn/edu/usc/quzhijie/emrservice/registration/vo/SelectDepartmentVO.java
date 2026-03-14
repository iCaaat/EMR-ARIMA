package cn.edu.usc.quzhijie.emrservice.registration.vo;

import lombok.Data;

@Data
public class SelectDepartmentVO {
    private Integer selectDepartmentId;
    private String selectDepartmentName;
    private String description;
    private String parentDepartmentName;
}
