package cn.edu.usc.quzhijie.emrservice.registration.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.registration.dto.AppointmentDTO;
import cn.edu.usc.quzhijie.emrservice.registration.dto.SlotsDTO;
import cn.edu.usc.quzhijie.emrservice.registration.service.RegistrationService;
import cn.edu.usc.quzhijie.emrservice.registration.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping("/departments")
    public Result<List<DepartmentVO>> getDepartment() {
        return Result.success("查询成功", registrationService.getDepartmentInfo());
    }

    @GetMapping("/dates")
    public Result<List<DateVO>> getSevenDays() {
        return Result.success(registrationService.getSevenDays());
    }

    @GetMapping("/doctors")
    public Result<List<ActiveDoctorVO>> getActiveDoctors(@RequestParam Integer departmentId, @RequestParam String workDate) {
        return Result.success(registrationService.getActiveDoctors(departmentId, workDate));
    }

    @GetMapping("/departments/{departmentId}")
    public Result<SelectDepartmentVO> selectDepartmentVOResult(@PathVariable Integer departmentId) {
        return Result.success(registrationService.selectDepartmentVOResult(departmentId));
    }

    @GetMapping("/schedules/{scheduleId}")
    public Result<SelectScheduleVO> selectScheduleVOResult(@PathVariable Integer scheduleId) {
        return Result.success(registrationService.selectScheduleVOResult(scheduleId));
    }

    @GetMapping("/period/{scheduleId}")
    public Result<List<PeriodVO>> getPeriodInfo(@PathVariable Integer scheduleId) {
        return Result.success(registrationService.getPeriodInfo(scheduleId));
    }

    @GetMapping("/slots")
    public Result<List<SlotsVO>> getSlots(@RequestParam Integer scheduleId,
                                          @RequestParam String period) {
        return Result.success(registrationService.getSlots(scheduleId, period));
    }

    @PostMapping("/appoint")
    public Result<Integer> appointRegistration(@RequestBody @Validated AppointmentDTO dto) {
        return Result.success(registrationService.appointRegistration(dto));
    }
}
