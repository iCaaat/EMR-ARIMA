package cn.edu.usc.quzhijie.emrservice.registration.mapper;

import cn.edu.usc.quzhijie.emrservice.registration.dto.AppointmentDTO;
import cn.edu.usc.quzhijie.emrservice.registration.entity.Appointment;
import cn.edu.usc.quzhijie.emrservice.registration.entity.Department;
import cn.edu.usc.quzhijie.emrservice.user.entity.DoctorExp;
import cn.edu.usc.quzhijie.emrservice.registration.entity.DoctorSchedule;
import cn.edu.usc.quzhijie.emrservice.registration.entity.ScheduleSlot;
import cn.edu.usc.quzhijie.emrservice.registration.vo.*;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface RegistrationMapper {
    List<Department> listDepartment();

    List<ActiveDoctorVO> listActiveDoctors(@Param("departmentId") Integer departmentId, @Param("workDate") String workDate);

    SelectDepartmentVO selectDepartmentVOById(@Param("departmentId") Integer departmentId);

    SelectScheduleVO selectScheduleVOById(@Param("scheduleId") Integer scheduleId);

    List<PeriodVO> getPeriodByScheduleId(@Param("scheduleId") Integer scheduleId);

    List<SlotsVO> getSlotsByPeriodAndScheduleId(@Param("scheduleId") Integer scheduleId, @Param("period") String period);

    ScheduleSlot getSlotById(@Param("slotId") Integer slotId);

    DoctorSchedule getScheduleById(@Param("scheduleId") Integer scheduleId);

    Integer insertAppointment(AppointmentDTO dto);

    Department getDepartmentById(@Param("departmentId") Integer departmentId);

    Appointment getAppointmentBySlotId(AppointmentDTO dto);

    List<UserAppointmentVO> listUserAppointments(@Param("belongingUid") Integer uid);
}
