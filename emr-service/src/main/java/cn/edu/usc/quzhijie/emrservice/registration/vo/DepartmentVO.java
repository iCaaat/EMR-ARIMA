package cn.edu.usc.quzhijie.emrservice.registration.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentVO {
    private Integer departmentId;
    private String name;
    private String description;
    private List<DepartmentVO> children = new ArrayList<>();


}
