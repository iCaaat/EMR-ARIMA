package cn.edu.usc.quzhijie.emrservice.record.service;

import cn.edu.usc.quzhijie.emrservice.record.dto.PostRecordDTO;

public interface MedicalService {
    byte[] getMedicalRecordPdf();

    Integer addMedicalRecord(Integer uid, PostRecordDTO dto);
}
