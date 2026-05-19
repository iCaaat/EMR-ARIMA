package cn.edu.usc.quzhijie.emrservice.schedule.mapper;

import cn.edu.usc.quzhijie.emrservice.common.entity.ScheduleSlot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleSlotMapper {
    Integer batchInsertSlots(@Param("list") List<ScheduleSlot> scheduleSlots);

    Integer selectByScheduleId(Integer scheduleId);
    Integer deleteByScheduleId(Integer scheduleId);

    Integer updateSlotStatusById(@Param("slotId") Integer slotId, @Param("status") String status);
}
