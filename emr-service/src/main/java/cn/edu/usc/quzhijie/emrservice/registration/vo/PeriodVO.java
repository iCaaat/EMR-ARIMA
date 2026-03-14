package cn.edu.usc.quzhijie.emrservice.registration.vo;

import lombok.Data;

@Data
public class PeriodVO {
    private Integer slotId;
    private Integer scheduleId;
    private String period;
    private Integer remainNumber;
}
