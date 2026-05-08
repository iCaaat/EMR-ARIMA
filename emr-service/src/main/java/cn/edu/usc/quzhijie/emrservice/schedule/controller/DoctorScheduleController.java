package cn.edu.usc.quzhijie.emrservice.schedule.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleAddDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleSearchDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.dto.ScheduleUpdateDTO;
import cn.edu.usc.quzhijie.emrservice.schedule.service.DoctorScheduleService;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.DoctorScheduleVO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleDeleteVO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleSearchVO;
import cn.edu.usc.quzhijie.emrservice.schedule.vo.ScheduleUpdateVO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public Result<Integer> addSchedule(@RequestBody @Validated ScheduleAddDTO dto) {
        return Result.success("添加成功", doctorScheduleService.addSchedule(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public Result<ScheduleDeleteVO> deleteSchedule(@PathVariable("id") Integer scheduleId) {
        return Result.success(doctorScheduleService.deleteSchedule(scheduleId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Result<ScheduleUpdateVO> updateSchedule(@RequestBody @Validated ScheduleUpdateDTO dto) {
        return Result.success(doctorScheduleService.updateSchedule(dto));
    }

    @PreAuthorize("hasAnyRole('DOCTOR')")
    @GetMapping("/doctor")
    public Result<List<DoctorScheduleVO>> getDoctorSchedule(Authentication authentication) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");

        return Result.success(doctorScheduleService.getDoctorSchedule(uid));
    }
}
