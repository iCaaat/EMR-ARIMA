package cn.edu.usc.quzhijie.emrservice.python.service;

import cn.edu.usc.quzhijie.emrservice.python.vo.ArimaPredictVO;

import java.util.List;

public interface PythonService {
    ArimaPredictVO arimaPredictByDepartment(Integer departmentId, Integer days);

    ArimaPredictVO arimaPredictByDepartmentTest(Integer departmentId, Integer days);
}
