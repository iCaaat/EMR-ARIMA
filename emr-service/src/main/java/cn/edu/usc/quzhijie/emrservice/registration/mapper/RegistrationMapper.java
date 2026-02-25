package cn.edu.usc.quzhijie.emrservice.registration.mapper;

import cn.edu.usc.quzhijie.emrservice.registration.entity.Department;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface RegistrationMapper {
    List<Department> listDepartment();
}
