package cn.edu.usc.quzhijie.emrservice.user.service;

import cn.edu.usc.quzhijie.emrservice.user.dto.PatientDetailDTO;
import cn.edu.usc.quzhijie.emrservice.user.vo.PatientDetailVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.PatientOverviewVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientSimpleVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientVO;

import java.util.List;

public interface PatientService {
    List<UserPatientVO> getUserPatients(Integer uid);

    PatientDetailVO getPatientDetail(Integer uid, Integer patientId);

    String updatePatientDetail(Integer uid, PatientDetailDTO dto);

    String addPatient(Integer uid, PatientDetailDTO dto);

    String deleteUserPatient(Integer uid, Integer patientId);

    List<UserPatientSimpleVO> getUserPatientsSimple(Integer uid);

    PatientOverviewVO getPatientOverview(Integer uid);
}
