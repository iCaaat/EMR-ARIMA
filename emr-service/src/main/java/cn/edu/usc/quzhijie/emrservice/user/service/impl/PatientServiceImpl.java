package cn.edu.usc.quzhijie.emrservice.user.service.impl;

import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.user.dto.PatientDetailDTO;
import cn.edu.usc.quzhijie.emrservice.user.entity.PatientExp;
import cn.edu.usc.quzhijie.emrservice.user.mapper.PatientMapper;
import cn.edu.usc.quzhijie.emrservice.user.service.PatientService;
import cn.edu.usc.quzhijie.emrservice.user.vo.PatientDetailVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientVO;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PatientDetailVO getPatientDetail(Integer uid, Integer patientId) {
        PatientExp patientExp = patientMapper.getPatientById(uid, patientId);
        if (patientExp == null) {
            throw new BizException("就诊人不存在");
        }
        PatientDetailVO patientDetailVO = new PatientDetailVO();
        if (patientExp.getStatus() == 0) {
            BeanUtils.copyProperties(patientExp, patientDetailVO);
        }

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

    @Override
    @Transactional
    public String updatePatientDetail(Integer uid, PatientDetailDTO dto) {
        if (dto.getPatientId() == null || dto.getPatientId() <= 0) {
            throw new BizException("就诊人id不合法");
        }
        PatientExp exp = patientMapper.getPatientById(uid, dto.getPatientId());
        if (exp == null || exp.getStatus() == 1) {
            throw new BizException("就诊人不存在");
        }
        Integer result = patientMapper.updatePatientDetailById(uid, dto);
        if (result < 1) {
            throw new BizException("更新就诊人信息失败");
        }
        return "更新成功";
    }

    @Override
    @Transactional
    public String addPatient(Integer uid, PatientDetailDTO dto) {
        if ("self".equals(dto.getRelation())) {
            throw new BizException("就诊人关系self已经存在");
        }
        PatientExp exp = patientMapper.getPatientByIdCard(uid, dto.getIdCard());
        if (exp != null && exp.getStatus() == 0) {
            throw new BizException("就诊人已存在");
        }

        if (StringUtils.isBlank(dto.getRelation())) {
            dto.setRelation("other");
        }
        if (StringUtils.isBlank(dto.getRelationDisplay())) {
            dto.setRelationDisplay("其他");
        }

        if (exp != null && exp.getStatus() == 1) {
            // 更新数据
            dto.setPatientId(exp.getPatientId());
            Integer result = patientMapper.updatePatientDetailStatusById(uid, dto);
            if (result < 1) {
                throw new BizException("添加就诊人信息失败");
            }
        } else {
            // 插入数据
            Integer result = patientMapper.insertPatient(uid, dto);
            if (result < 1) {
                throw new BizException("添加就诊人信息失败");
            }
        }


        return "添加成功";
    }

    @Override
    @Transactional
    public String deleteUserPatient(Integer uid, Integer patientId) {
        PatientExp patientExp = patientMapper.getPatientById(uid, patientId);
        if (patientExp == null) {
            throw new BizException("就诊人不存在");
        }
        if ("self".equals(patientExp.getRelation())) {
            throw new BizException("无法删除此就诊人信息");
        }

        Integer res = patientMapper.updatePatientStatusById(uid, patientId, 1);
        return "删除成功";
    }
}
