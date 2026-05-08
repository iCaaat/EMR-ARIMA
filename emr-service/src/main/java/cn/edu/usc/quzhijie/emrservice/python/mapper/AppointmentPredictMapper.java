package cn.edu.usc.quzhijie.emrservice.python.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppointmentPredictMapper {
    List<Map<String, Object>> listPredictData(
            @Param("departmentId") Integer departmentId
    );
}
