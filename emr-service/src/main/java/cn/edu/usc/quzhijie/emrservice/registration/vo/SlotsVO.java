package cn.edu.usc.quzhijie.emrservice.registration.vo;

import lombok.Data;

@Data
public class SlotsVO {
    private Integer slotId;
    private Integer scheduleId;
    private String period;
    private Integer seqNo;
    private String displayNo;
    private Integer fee;
    private String status;
}
