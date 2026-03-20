package cn.edu.usc.quzhijie.emrservice.user.service;

import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientVO;

import java.util.List;

public interface PatientService {
    List<UserPatientVO> getUserPatients(Integer uid);
}
