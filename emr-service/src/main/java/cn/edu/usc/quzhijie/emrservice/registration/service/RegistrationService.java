package cn.edu.usc.quzhijie.emrservice.registration.service;

import cn.edu.usc.quzhijie.emrservice.registration.dto.AppointmentDTO;
import cn.edu.usc.quzhijie.emrservice.registration.dto.SlotsDTO;
import cn.edu.usc.quzhijie.emrservice.registration.vo.*;

import java.util.List;

public interface RegistrationService {
    List<DepartmentVO> getDepartmentInfo();

    List<DateVO> getSevenDays();

    List<ActiveDoctorVO> getActiveDoctors(Integer departmentId, String date);

    SelectDepartmentVO selectDepartmentVOResult(Integer departmentId);

    SelectScheduleVO selectScheduleVOResult(Integer scheduleId);

    List<PeriodVO> getPeriodInfo(Integer scheduleId);

    List<SlotsVO> getSlots(Integer scheduleId, String period);

    Integer appointRegistration(AppointmentDTO dto);
}
