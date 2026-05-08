package cn.edu.usc.quzhijie.emrservice.registration.controller;

import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.registration.dto.AppointmentDTO;
import cn.edu.usc.quzhijie.emrservice.registration.dto.AppointmentFilterDTO;
import cn.edu.usc.quzhijie.emrservice.registration.dto.DoctorAppointmentFilterDTO;
import cn.edu.usc.quzhijie.emrservice.registration.dto.SlotsDTO;
import cn.edu.usc.quzhijie.emrservice.registration.service.RegistrationService;
import cn.edu.usc.quzhijie.emrservice.registration.vo.*;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Validated
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

    @PostMapping("/appoint/search")
    public Result<List<UserAppointmentVO>> getUserAppointments(Authentication authentication,
                                                               @RequestBody AppointmentFilterDTO dto) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");
        return Result.success(registrationService.getUserAppointments(uid, dto));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/appoint/list")
    public Result<PageResult<DoctorAppointmentsVO>> getDoctorAppointments(Authentication authentication,
                                                                                @RequestBody DoctorAppointmentFilterDTO dto) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");

        return Result.success(registrationService.getDoctorAppointments(uid, dto));
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/appoint/{appointmentId}/status")
    public Result<String> updateAppointmentStatus(Authentication authentication,

                                                  @PathVariable
                                                  @NotNull(message = "预约ID不能为空")
                                                  Integer appointmentId,

                                                  @RequestParam
                                                  @NotNull(message = "状态不能为空")
                                                  @Min(value = 0, message = "状态值最小为0")
                                                  @Max(value = 3, message = "状态值最大为3")
                                                  Integer status) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");
        registrationService.updateAppointmentStatus(uid, appointmentId, status);
        return Result.success("更新成功");
    }
}
