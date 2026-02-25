package cn.edu.usc.quzhijie.emrservice.registration.service;

import cn.edu.usc.quzhijie.emrservice.registration.entity.Department;
import cn.edu.usc.quzhijie.emrservice.registration.vo.DepartmentVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RegistrationService {
    List<DepartmentVO> getDepartmentInfo();
}
