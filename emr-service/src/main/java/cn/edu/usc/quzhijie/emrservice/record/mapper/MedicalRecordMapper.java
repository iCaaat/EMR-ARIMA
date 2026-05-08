package cn.edu.usc.quzhijie.emrservice.record.mapper;

import cn.edu.usc.quzhijie.emrservice.record.dto.PostRecordDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MedicalRecordMapper {
    Integer insertMedicalRecord(PostRecordDTO dto);
}
