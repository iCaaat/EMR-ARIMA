package cn.edu.usc.quzhijie.emrservice.user.service.impl;

import cn.edu.usc.quzhijie.emrservice.common.entity.Appointment;
import cn.edu.usc.quzhijie.emrservice.common.entity.MedicalRecord;
import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.record.mapper.MedicalRecordMapper;
import cn.edu.usc.quzhijie.emrservice.registration.mapper.RegistrationMapper;
import cn.edu.usc.quzhijie.emrservice.user.dto.PatientDetailDTO;
import cn.edu.usc.quzhijie.emrservice.common.entity.PatientExp;
import cn.edu.usc.quzhijie.emrservice.user.mapper.PatientMapper;
import cn.edu.usc.quzhijie.emrservice.user.service.PatientService;
import cn.edu.usc.quzhijie.emrservice.user.util.InfoUtils;
import cn.edu.usc.quzhijie.emrservice.user.vo.PatientDetailVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.PatientOverviewVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientSimpleVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserPatientVO;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientMapper patientMapper;
    private final RegistrationMapper registrationMapper;
    private final MedicalRecordMapper medicalRecordMapper;
    private final InfoUtils infoUtils;

    @Override
    public List<UserPatientVO> getUserPatients(Integer uid) {
        List<PatientExp> patients = patientMapper.getPatientsByUid(uid);

        List<UserPatientVO> list = new ArrayList<>();
        for (PatientExp patient : patients) {
            String name = InfoUtils.maskName(patient.getRealName());
            String idCard = InfoUtils.maskIdCard(patient.getIdCard());

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
    public List<UserPatientSimpleVO> getUserPatientsSimple(Integer uid) {
        List<PatientExp> patients = patientMapper.getPatientsByUid(uid);

        List<UserPatientSimpleVO> list = new ArrayList<>();
        for (PatientExp patient : patients) {
            String idCard = InfoUtils.maskIdCard(patient.getIdCard());
            UserPatientSimpleVO vo = new UserPatientSimpleVO();
            vo.setPatientId(patient.getPatientId());
            vo.setRealName(patient.getRealName());
            vo.setIdCardSecret(idCard);
            list.add(vo);
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

    @Override
    @Transactional(readOnly = true)
    public PatientOverviewVO getPatientOverview(Integer uid) {
        if (uid == null) {
            throw new IllegalArgumentException("uid不能为空");
        }

        PatientOverviewVO vo = new PatientOverviewVO();

        // 1. 医院介绍，可以先写死；以后也可以改成从配置表查
        vo.setHospitalIntro(buildHospitalIntro());

        // 2. 查询当前账号下所有有效就诊人
        List<Integer> patientIds = patientMapper.selectActivePatientIdsByUid(uid);

        Integer patientCount = patientMapper.countActivePatientsByUid(uid);
        vo.setPatientCount(patientCount == null ? 0 : patientCount);

        PatientOverviewVO.PatientBriefVO defaultPatient = new PatientOverviewVO.PatientBriefVO();
        PatientExp defaultPatientExp = patientMapper.selectDefaultPatientByUid(uid);
        if (defaultPatientExp != null) {
            BeanUtils.copyProperties(defaultPatientExp, defaultPatient);
        }
        vo.setDefaultPatient(defaultPatient);

        // 没有就诊人时，直接返回空首页
        if (patientIds == null || patientIds.isEmpty()) {
            vo.setTotalVisitCount(0);
            vo.setMedicalRecordCount(0);
            vo.setRecentAppointments(Collections.emptyList());
            vo.setNextAppointment(null);
            vo.setLatestMedicalRecord(null);
            return vo;
        }

        // 3. 下一次待就诊预约
        PatientOverviewVO.AppointmentBriefVO nextAppointment = new PatientOverviewVO.AppointmentBriefVO();
        Appointment appointment = registrationMapper.selectNextAppointment(patientIds);
        if (appointment != null) {
            BeanUtils.copyProperties(appointment, nextAppointment);
        } else {
            nextAppointment = null;
        }
        vo.setNextAppointment(nextAppointment);

        // 4. 累计就诊次数：建议按已完成病历数量统计
        Integer totalVisitCount = medicalRecordMapper.countCompletedRecords(patientIds);
        vo.setTotalVisitCount(totalVisitCount == null ? 0 : totalVisitCount);

        // 5. 病历数量
        Integer medicalRecordCount = medicalRecordMapper.countCompletedRecords(patientIds);
        vo.setMedicalRecordCount(medicalRecordCount == null ? 0 : medicalRecordCount);

        // 6. 最近病历
        PatientOverviewVO.MedicalRecordBriefVO latestMedicalRecord = new PatientOverviewVO.MedicalRecordBriefVO();
        MedicalRecord medicalRecord = medicalRecordMapper.selectLatestCompletedRecord(patientIds);
        if (medicalRecord != null) {
            BeanUtils.copyProperties(medicalRecord, latestMedicalRecord);
        } else {
            latestMedicalRecord = null;
        }
        vo.setLatestMedicalRecord(latestMedicalRecord);

        // 7. 最近挂号记录，首页展示 5 条即可
        List<PatientOverviewVO.AppointmentBriefVO> recentAppointments = new ArrayList<>();
        List<Appointment> appointments = registrationMapper.selectRecentAppointments(patientIds, 5);
        if (appointments != null) {
            for (Appointment app : appointments) {
                PatientOverviewVO.AppointmentBriefVO appBriefVO = new PatientOverviewVO.AppointmentBriefVO();
                BeanUtils.copyProperties(app, appBriefVO);
                recentAppointments.add(appBriefVO);
            }
        }
        vo.setRecentAppointments(recentAppointments);

        return vo;
    }

    private PatientOverviewVO.HospitalIntroVO buildHospitalIntro() {
        PatientOverviewVO.HospitalIntroVO intro = new PatientOverviewVO.HospitalIntroVO();
        intro.setTitle("医院服务介绍");
        intro.setDescription(
                "本系统支持线上预约挂号、就诊人管理、挂号记录查询和病历信息查看，" +
                        "患者可以通过首页快速查看近期预约、历史就诊情况和病历信息。"
        );
        intro.setImageUrl("https://example.com/hospital.jpg");
        return intro;
    }
}
