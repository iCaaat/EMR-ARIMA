package cn.edu.usc.quzhijie.emrservice.schedule.dto;

import cn.edu.usc.quzhijie.emrservice.common.query.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleSearchDTO extends PageQuery {
    private String doctorName;
    private Integer departmentId;
    private String workDate;
    private Integer status;
}
