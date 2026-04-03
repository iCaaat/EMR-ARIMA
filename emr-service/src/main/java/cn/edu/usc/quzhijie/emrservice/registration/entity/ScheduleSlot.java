package cn.edu.usc.quzhijie.emrservice.registration.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ScheduleSlot {
    private Integer slotId;
    private Integer scheduleId;
    private String period;
    private Integer seqNo;
    private String displayNo;
    private LocalDate visitDate;
    private Integer fee;
    private String status;
    private LocalDateTime createTime;
}
