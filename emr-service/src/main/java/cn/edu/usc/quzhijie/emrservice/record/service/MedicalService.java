package cn.edu.usc.quzhijie.emrservice.record.service;

import cn.edu.usc.quzhijie.emrservice.record.dto.PostRecordDTO;
import cn.edu.usc.quzhijie.emrservice.record.vo.MyRecordVO;

import java.util.List;

public interface MedicalService {
    byte[] getMedicalRecordPdf();

    Integer addMedicalRecord(Integer uid, PostRecordDTO dto);

    List<MyRecordVO> getMyRecords(Integer uid, Integer patientId);
}
