package cn.edu.usc.quzhijie.emrservice.schedule.mapper;

import cn.edu.usc.quzhijie.emrservice.common.entity.DoctorSchedule;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleSearchDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleUpdateDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.DoctorScheduleVO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleSearchVO;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoctorScheduleMapper {

    Long countScheduleByCondition(ScheduleSearchDTO dto);

    List<ScheduleSearchVO> listScheduleByCondition(ScheduleSearchDTO dto);

    Integer batchInsert(@Param("list") List<DoctorSchedule> list);

    Integer deleteScheduleById(Integer scheduleId);

    Integer updateSchedule(ScheduleUpdateDTO dto);

    DoctorSchedule selectByScheduleId(@Param("scheduleId") Integer scheduleId);

    List<DoctorScheduleVO> listScheduleByDoctorId(Integer doctorId);
}
