package cn.edu.usc.quzhijie.emrservice.schedule.service;

import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleAddDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleSearchDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleSearchVO;

import java.util.List;

public interface DoctorScheduleService {

    PageResult<ScheduleSearchVO> searchSchedule(ScheduleSearchDTO dto);

    Integer addSchedule(ScheduleAddDTO dto);
}
