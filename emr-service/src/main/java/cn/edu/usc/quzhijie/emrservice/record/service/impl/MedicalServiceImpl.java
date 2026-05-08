package cn.edu.usc.quzhijie.emrservice.record.service.impl;

import cn.edu.usc.quzhijie.emrservice.common.entity.Appointment;
import cn.edu.usc.quzhijie.emrservice.common.entity.DoctorExp;
import cn.edu.usc.quzhijie.emrservice.common.entity.PatientExp;
import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.common.util.PdfGenerator;
import cn.edu.usc.quzhijie.emrservice.record.dto.PostRecordDTO;
import cn.edu.usc.quzhijie.emrservice.record.mapper.MedicalRecordMapper;
import cn.edu.usc.quzhijie.emrservice.record.service.MedicalService;
import cn.edu.usc.quzhijie.emrservice.record.vo.MyRecordVO;
import cn.edu.usc.quzhijie.emrservice.registration.mapper.RegistrationMapper;
import cn.edu.usc.quzhijie.emrservice.user.mapper.DoctorMapper;
import cn.edu.usc.quzhijie.emrservice.user.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalService {
    private final PdfGenerator pdfGenerator;
    private final RegistrationMapper registrationMapper;
    private final MedicalRecordMapper medicalRecordMapper;
    private final PatientMapper patientMapper;

    @Override
    public byte[] getMedicalRecordPdf() {
        Context context = new Context();
        context.setVariable("name", "张三");
        context.setVariable("age", 25);
        context.setVariable("diagnosis", "感冒");
        return pdfGenerator.generatePdf("medical-record", context);
    }

    @Override
    @Transactional
    public Integer addMedicalRecord(Integer uid, PostRecordDTO dto) {
        Appointment appointment = registrationMapper.getAppointmentById(dto.getAppointmentId());
        if (appointment == null) {
            throw new BizException("预约不存在");
        }

        dto.setPatientId(appointment.getPatientId());
        dto.setPatientName(appointment.getPatientName());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setDoctorName(appointment.getDoctorName());
        dto.setDepartmentName(appointment.getDepartmentName());
        dto.setVisitDate(appointment.getVisitDate());

        Integer result = medicalRecordMapper.insertMedicalRecord(dto);
        if (result == null || result <= 0) {
            throw new BizException("病历提交失败");
        }

        // 修改预约状态为已完成
        Integer result2 = registrationMapper.updateAppointmentStatus(dto.getAppointmentId(), 2);
        if (result2 == null || result2 <= 0) {
            throw new BizException("预约状态更新失败");
        }

        return result;
    }

    @Override
    public List<MyRecordVO> getMyRecords(Integer uid, Integer patientId) {
        PatientExp patientExp = patientMapper.getPatientById(uid, patientId);
        if (patientExp == null) {
            throw new BizException("就诊人不存在");
        }

        return medicalRecordMapper.listByPatientId(patientId);
    }
}
