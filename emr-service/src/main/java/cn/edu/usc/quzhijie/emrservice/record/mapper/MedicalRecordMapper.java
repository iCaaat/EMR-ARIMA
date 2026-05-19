package cn.edu.usc.quzhijie.emrservice.record.mapper;

import cn.edu.usc.quzhijie.emrservice.common.entity.MedicalRecord;
import cn.edu.usc.quzhijie.emrservice.record.dto.PostRecordDTO;
import cn.edu.usc.quzhijie.emrservice.record.vo.MyRecordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MedicalRecordMapper {
    Integer insertMedicalRecord(PostRecordDTO dto);

    List<MyRecordVO> listByPatientId(Integer patientId);

    Integer countCompletedRecords(List<Integer> patientIds);

    MedicalRecord selectLatestCompletedRecord(List<Integer> patientIds);
}
