package cn.edu.usc.quzhijie.emrservice.registration.service.impl;

import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.registration.dto.AppointmentDTO;
import cn.edu.usc.quzhijie.emrservice.registration.dto.AppointmentFilterDTO;
import cn.edu.usc.quzhijie.emrservice.common.entity.Appointment;
import cn.edu.usc.quzhijie.emrservice.common.entity.Department;
import cn.edu.usc.quzhijie.emrservice.common.entity.DoctorSchedule;
import cn.edu.usc.quzhijie.emrservice.common.entity.ScheduleSlot;
import cn.edu.usc.quzhijie.emrservice.registration.mapper.RegistrationMapper;
import cn.edu.usc.quzhijie.emrservice.registration.service.RegistrationService;
import cn.edu.usc.quzhijie.emrservice.registration.vo.*;
import cn.edu.usc.quzhijie.emrservice.common.entity.DoctorExp;
import cn.edu.usc.quzhijie.emrservice.user.mapper.DoctorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationMapper registrationMapper;
    private final DoctorMapper doctorMapper;
    private final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<DepartmentVO> getDepartmentInfo() {
        // 1.获取科室信息
        List<Department> departments = registrationMapper.listDepartment();

        // 2.转换成VO并放入Map
        Map<Integer, DepartmentVO> map = new HashMap<>();
        for (Department department : departments) {
            DepartmentVO vo = new DepartmentVO();
            vo.setDepartmentId(department.getDepartmentId());
            vo.setName(department.getName());
            vo.setDescription(department.getDescription());
            map.put(department.getDepartmentId(), vo);
        }

        // 3.构建父子关系
        List<DepartmentVO> rootList = new ArrayList<>();

        for (Department department : departments) {
            DepartmentVO current = map.get(department.getDepartmentId());

            // 根节点
            if (department.getParentId() == null || department.getParentId() == 0) {
                rootList.add(current);
            } else {
                // 找父节点
                DepartmentVO parent = map.get(department.getParentId());
                if (parent != null) {
                    parent.getChildren().add(current);
                }
            }
        }


        return rootList;
    }

    @Override
    public List<DateVO> getSevenDays() {
        List<DateVO> list = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {

            LocalDate date = today.plusDays(i + 1);

            DateVO vo = new DateVO();
            vo.setDate(date.toString());
            vo.setMonthDay(date.format(DateTimeFormatter.ofPattern("MM-dd")));
            vo.setWeek(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));

            list.add(vo);
        }
        return list;
    }

    @Override
    public List<ActiveDoctorVO> getActiveDoctors(Integer departmentId, String date) {
        // 1.查出在岗医生信息
        List<ActiveDoctorVO> list = registrationMapper.listActiveDoctors(departmentId, date);
        // 2.字段展示处理
        for (ActiveDoctorVO activeDoctorVO : list) {
            if ("normal".equals(activeDoctorVO.getOutpatientType())) {
                activeDoctorVO.setOutpatientType("普通门诊");
            } else if ("expert".equals(activeDoctorVO.getOutpatientType())) {
                activeDoctorVO.setOutpatientType("专家门诊");
            }
        }
        return list;
    }

    @Override
    public SelectDepartmentVO selectDepartmentVOResult(Integer departmentId) {
        return registrationMapper.selectDepartmentVOById(departmentId);
    }

    @Override
    public SelectScheduleVO selectScheduleVOResult(Integer scheduleId) {
        return registrationMapper.selectScheduleVOById(scheduleId);
    }

    @Override
    public List<PeriodVO> getPeriodInfo(Integer scheduleId) {
        List<PeriodVO> list = registrationMapper.getPeriodByScheduleId(scheduleId);
        for (PeriodVO periodVO : list) {
            if ("am".equals(periodVO.getPeriod())) {
                periodVO.setDisplayPeriod("上午(A)");
            } else if ("pm".equals(periodVO.getPeriod())) {
                periodVO.setDisplayPeriod("下午(P)");
            }
        }
        return list;
    }

    @Override
    public List<SlotsVO> getSlots(Integer scheduleId, String period) {
        return registrationMapper.getSlotsByPeriodAndScheduleId(scheduleId, period);
    }

    @Override
    @Transactional
    public Integer appointRegistration(AppointmentDTO dto) {
        DoctorSchedule schedule = registrationMapper.getScheduleById(dto.getScheduleId());
        if (schedule == null || schedule.getStatus() != 0) {
            throw new BizException("所选在岗医生信息出错");
        }

        DoctorExp doctor = doctorMapper.getDoctorById(schedule.getDoctorId());
        dto.setDoctorId(doctor.getDoctorId());
        dto.setDoctorName(doctor.getRealName());
        Department department = registrationMapper.getDepartmentById(schedule.getDepartmentId());
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getName());

        ScheduleSlot slot = registrationMapper.getSlotById(dto.getSlotId());
        if (slot == null) {
            throw new BizException("所选择号源不存在");
        }
        if (!"available".equals(slot.getStatus())) {
            throw new BizException("所选号源无法预约");
        }

        dto.setFee(slot.getFee());
        dto.setPeriod(slot.getPeriod());
        dto.setQueueNumber(slot.getSeqNo());
        dto.setVisitDate(slot.getVisitDate());

        Appointment appointment = registrationMapper.getAppointmentBySlotId(dto);
        if (appointment != null) {
            throw new BizException("所选号源已被预约或就诊人已预约了同一时间段的号源");
        }
        Integer result = registrationMapper.insertAppointment(dto);
        if (result != 1) {
            throw new BizException("预约失败");
        }

        return result;
    }

    @Override
    public List<UserAppointmentVO> getUserAppointments(Integer uid, AppointmentFilterDTO dto) {
        List<UserAppointmentVO> list = registrationMapper.listUserAppointments(uid, dto);
        for (UserAppointmentVO userAppointmentVO : list) {
            if (userAppointmentVO.getStatus() == 0) {
                userAppointmentVO.setDisplayStatus("待就诊");
            } else if (userAppointmentVO.getStatus() == 1) {
                userAppointmentVO.setDisplayStatus("就诊中");
            } else if (userAppointmentVO.getStatus() == 2) {
                userAppointmentVO.setDisplayStatus("已完成");
            } else if (userAppointmentVO.getStatus() == 3) {
                userAppointmentVO.setDisplayStatus("已取消");
            }

            if ("am".equals(userAppointmentVO.getPeriod())) {
                userAppointmentVO.setDisplayPeriod("上午(A)");
            } else if ("pm".equals(userAppointmentVO.getPeriod())) {
                userAppointmentVO.setDisplayPeriod("下午(P)");
            }
        }

        return list;
    }
}
