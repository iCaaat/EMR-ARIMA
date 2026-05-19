package cn.edu.usc.quzhijie.emrservice.registration.mapper;

import cn.edu.usc.quzhijie.emrservice.registration.dto.AppointmentDTO;
import cn.edu.usc.quzhijie.emrservice.registration.dto.AppointmentFilterDTO;
import cn.edu.usc.quzhijie.emrservice.common.entity.Appointment;
import cn.edu.usc.quzhijie.emrservice.common.entity.Department;
import cn.edu.usc.quzhijie.emrservice.common.entity.DoctorSchedule;
import cn.edu.usc.quzhijie.emrservice.common.entity.ScheduleSlot;
import cn.edu.usc.quzhijie.emrservice.registration.dto.DoctorAppointmentFilterDTO;
import cn.edu.usc.quzhijie.emrservice.registration.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    List<UserAppointmentVO> listUserAppointments(@Param("belongingUid") Integer uid, @Param("dto") AppointmentFilterDTO dto);

    List<Map<String, Object>> getDailyAppointmentCount(@Param("departmentId") Integer departmentId);

    List<DoctorAppointmentsVO> getDoctorAppointmentsByCondition(@Param("doctorId") Integer doctorId, @Param("dto") DoctorAppointmentFilterDTO dto);
    Long countDoctorAppointmentsByCondition(@Param("doctorId") Integer doctorId, @Param("dto") DoctorAppointmentFilterDTO dto);

    Integer updateAppointmentStatusById(@Param("doctorId") Integer doctorId,@Param("appointmentId") Integer appointmentId, @Param("status") Integer status);

    Appointment getAppointmentById(Integer appointmentId);

    Integer updateAppointmentStatus(@Param("appointmentId") Integer appointmentId, @Param("status") Integer status);

    Appointment selectNextAppointment(@Param("patientIds") List<Integer> patientIds);
    List<Appointment> selectRecentAppointments(@Param("patientIds") List<Integer> patientIds, @Param("limit") Integer limit);
}
