package cn.edu.usc.quzhijie.emrservice.user.service.impl;

import cn.edu.usc.quzhijie.emrservice.user.entity.PatientExp;
import cn.edu.usc.quzhijie.emrservice.user.mapper.PatientMapper;
import cn.edu.usc.quzhijie.emrservice.user.service.PatientService;
import cn.edu.usc.quzhijie.emrservice.user.vo.PatientDetailVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientVO;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientMapper patientMapper;

    @Override
    public List<UserPatientVO> getUserPatients(Integer uid) {
        List<PatientExp> patients = patientMapper.getPatientsByUid(uid);

        List<UserPatientVO> list = new ArrayList<>();
        for (PatientExp patient : patients) {
            String name = maskName(patient.getRealName());
            String idCard = maskIdCard(patient.getIdCard());

            UserPatientVO userPatientVO = new UserPatientVO();
            userPatientVO.setPatientId(patient.getPatientId());
            userPatientVO.setBelongingUid(patient.getBelongingUid());
            userPatientVO.setRelation(patient.getRelation());
            userPatientVO.setRelationDisplay(patient.getRelationDisplay());
            userPatientVO.setRealNameSecret(name);
            userPatientVO.setIdCardSecret(idCard);
            list.add(userPatientVO);
        }
        return list;
    }

    @Override
    public PatientDetailVO getPatientDetail(Integer belongingUid, Integer patientId) {
        PatientExp patientExp = patientMapper.getPatientById(belongingUid, patientId);
        PatientDetailVO patientDetailVO = new PatientDetailVO();
        patientDetailVO.setPatientId(patientExp.getPatientId());
        patientDetailVO.setBelongingUid(patientExp.getBelongingUid());
        return patientDetailVO;
    }

    // 脱敏
    private String maskName(String name) {
        if (name == null || name.isEmpty()) return name;

        int length = name.length();
        if (length == 1) {
            return name;
        } else if (length == 2) {
            return name.charAt(0) + "*";
        } else {
            return name.charAt(0) + "*" + name.substring(length - 1);
        }
    }
    private String maskIdCard(String id) {
        if (id == null || id.length() < 8) return id;
        return id.substring(0, 6) + "**********" + id.substring(id.length() - 2);
    }
}
