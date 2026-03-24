package cn.edu.usc.quzhijie.emrservice.user.mapper;

import cn.edu.usc.quzhijie.emrservice.user.entity.PatientExp;
import cn.edu.usc.quzhijie.emrservice.user.vo.PatientDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatientMapper {
    // uid查询就诊人信息
    List<PatientExp> getPatientsByUid(@Param("uid") Integer uid);

    PatientExp getPatientById(@Param("belongingUid") Integer belongingUid, @Param("patientId") Integer patientId);
}
