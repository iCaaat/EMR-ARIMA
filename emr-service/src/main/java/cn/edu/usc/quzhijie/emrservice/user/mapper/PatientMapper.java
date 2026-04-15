package cn.edu.usc.quzhijie.emrservice.user.mapper;

import cn.edu.usc.quzhijie.emrservice.user.dto.PatientDetailDTO;
import cn.edu.usc.quzhijie.emrservice.common.entity.PatientExp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatientMapper {
    // uid查询就诊人信息
    List<PatientExp> getPatientsByUid(@Param("uid") Integer uid);

    PatientExp getPatientById(@Param("belongingUid") Integer uid, @Param("patientId") Integer patientId);

    Integer updatePatientDetailById(@Param("belongingUid") Integer uid, @Param("patient") PatientDetailDTO dto);
    Integer updatePatientDetailStatusById(@Param("belongingUid") Integer uid, @Param("patient") PatientDetailDTO dto);

    PatientExp getPatientByIdCard(@Param("belongingUid") Integer uid, @Param("idCard") String idCard);

    Integer insertPatient(@Param("belongingUid") Integer uid, @Param("patient") PatientDetailDTO dto);

    Integer deletePatientById(@Param("belongingUid") Integer uid,@Param("patientId") Integer patientId);

    Integer updatePatientStatusById(@Param("belongingUid") Integer uid, @Param("patientId") Integer patientId, @Param("status") Integer status);
}
