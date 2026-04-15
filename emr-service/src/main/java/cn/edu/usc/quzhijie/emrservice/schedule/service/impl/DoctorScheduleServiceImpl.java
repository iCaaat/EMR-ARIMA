package cn.edu.usc.quzhijie.emrservice.schedule.service.impl;

import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleSearchDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.mapper.DoctorScheduleMapper;
import cn.edu.usc.quzhijie.emrservice.schedule.service.DoctorScheduleService;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {
    private final DoctorScheduleMapper doctorScheduleMapper;

    @Override
    public PageResult<ScheduleSearchVO> searchSchedule(ScheduleSearchDTO dto) {
        Long total = doctorScheduleMapper.countScheduleByCondition(dto);

        if (total == 0) {
            return PageResult.empty();
        }

        List<ScheduleSearchVO> list = doctorScheduleMapper.listScheduleByCondition(dto);

        return new PageResult<>(total, dto.getPageNum(), dto.getPageSize(), list);
    }
}
