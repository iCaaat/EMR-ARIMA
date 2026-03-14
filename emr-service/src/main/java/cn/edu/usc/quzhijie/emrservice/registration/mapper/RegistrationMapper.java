package cn.edu.usc.quzhijie.emrservice.registration.mapper;

import cn.edu.usc.quzhijie.emrservice.registration.entity.Department;
import cn.edu.usc.quzhijie.emrservice.registration.entity.DoctorExp;
import cn.edu.usc.quzhijie.emrservice.registration.vo.ActiveDoctorVO;
import cn.edu.usc.quzhijie.emrservice.registration.vo.PeriodVO;
import cn.edu.usc.quzhijie.emrservice.registration.vo.SelectDepartmentVO;
import cn.edu.usc.quzhijie.emrservice.registration.vo.SelectScheduleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface RegistrationMapper {
    List<Department> listDepartment();

    List<DoctorExp> selectDoctorsOnWorkByDepartmentId(Integer departmentId);

    List<ActiveDoctorVO> listActiveDoctors(@Param("departmentId") Integer departmentId, @Param("workDate") String workDate);

    SelectDepartmentVO selectDepartmentVOById(@Param("departmentId") Integer departmentId);

    SelectScheduleVO selectScheduleVOById(@Param("scheduleId") Integer scheduleId);

    List<PeriodVO> getPeriodByScheduleId(@Param("scheduleId") Integer scheduleId);
}
