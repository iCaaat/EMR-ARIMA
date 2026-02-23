package cn.edu.usc.quzhijie.emrservice.user.mapper;

import cn.edu.usc.quzhijie.emrservice.user.entity.Role;
import cn.edu.usc.quzhijie.emrservice.user.entity.UserBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserBase selectByUsername(@Param("username") String username);

    Role selectRoleByUid(@Param("uid") int uid);
}
