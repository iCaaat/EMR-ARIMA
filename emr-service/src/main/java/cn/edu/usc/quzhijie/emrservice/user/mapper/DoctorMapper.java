package cn.edu.usc.quzhijie.emrservice.user.mapper;

import cn.edu.usc.quzhijie.emrservice.user.entity.DoctorExp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoctorMapper {
    List<DoctorExp> selectDoctorsOnWorkByDepartmentId(Integer departmentId);

    DoctorExp getDoctorById(@Param("doctorId") Integer doctorId);
}
