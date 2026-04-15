package cn.edu.usc.quzhijie.emrservice.schedule.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleAddDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleSearchDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.service.DoctorScheduleService;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(("/schedules"))
@RequiredArgsConstructor
public class DoctorScheduleController {
    private final DoctorScheduleService doctorScheduleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/search")
    public Result<PageResult<ScheduleSearchVO>> searchSchedule(@RequestBody ScheduleSearchDTO dto) {
        return Result.success("查询成功", doctorScheduleService.searchSchedule(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public Result<Integer> addSchedule(@RequestBody @Validated ScheduleAddDTO dto) {
        return Result.success("添加成功", doctorScheduleService.addSchedule(dto));
    }
}
