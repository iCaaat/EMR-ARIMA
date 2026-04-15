package cn.edu.usc.quzhijie.emrservice.user.dto;

import cn.edu.usc.quzhijie.emrservice.common.query.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorSimpleDTO extends PageQuery {
    private Integer departmentId;
    private String doctorName;
}
