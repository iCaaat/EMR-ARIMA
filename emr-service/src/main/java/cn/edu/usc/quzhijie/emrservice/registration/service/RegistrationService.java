package cn.edu.usc.quzhijie.emrservice.registration.service;

import cn.edu.usc.quzhijie.emrservice.registration.vo.DateVO;
import cn.edu.usc.quzhijie.emrservice.registration.vo.DepartmentVO;
import cn.edu.usc.quzhijie.emrservice.registration.vo.ActiveDoctorVO;
import cn.edu.usc.quzhijie.emrservice.registration.vo.SelectDepartmentVO;

import java.util.List;

public interface RegistrationService {
    List<DepartmentVO> getDepartmentInfo();

    List<DateVO> getSevenDays();

    List<ActiveDoctorVO> getActiveDoctors(Integer departmentId, String date);

    SelectDepartmentVO selectDepartmentVOResult(Integer departmentId);
}
