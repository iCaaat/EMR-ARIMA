package cn.edu.usc.quzhijie.emrservice.schedule.mapper;

import cn.edu.usc.quzhijie.emrservice.common.entity.DoctorSchedule;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleSearchDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoctorScheduleMapper {

    Long countScheduleByCondition(ScheduleSearchDTO dto);

    List<ScheduleSearchVO> listScheduleByCondition(ScheduleSearchDTO dto);

    Integer batchInsert(@Param("list") List<DoctorSchedule> list);

}
