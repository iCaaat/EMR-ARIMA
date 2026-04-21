package cn.edu.usc.quzhijie.emrservice.schedule.service;

import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleAddDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleSearchDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleUpdateDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleDeleteVO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleSearchVO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleUpdateVO;

import java.util.List;

public interface DoctorScheduleService {

    PageResult<ScheduleSearchVO> searchSchedule(ScheduleSearchDTO dto);

    Integer addSchedule(ScheduleAddDTO dto);

    ScheduleDeleteVO deleteSchedule(Integer scheduleId);

    ScheduleUpdateVO updateSchedule(ScheduleUpdateDTO dto);
}
