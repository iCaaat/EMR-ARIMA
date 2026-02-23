package cn.edu.usc.quzhijie.userservice.mapper;

import cn.edu.usc.quzhijie.userservice.entity.Role;
import cn.edu.usc.quzhijie.userservice.entity.UserBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    UserBase selectByUsername(@Param("username") String username);

    Role selectRoleByUid(@Param("uid") int uid);
}
