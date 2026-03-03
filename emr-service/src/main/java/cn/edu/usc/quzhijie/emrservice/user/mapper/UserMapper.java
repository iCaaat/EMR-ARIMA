package cn.edu.usc.quzhijie.emrservice.user.mapper;

import cn.edu.usc.quzhijie.emrservice.user.entity.Role;
import cn.edu.usc.quzhijie.emrservice.user.entity.UserBase;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserBase selectByUsername(@Param("username") String username);
    UserBase selectByUid(@Param("uid") Integer uid);

    Role selectRoleByUid(@Param("uid") Integer uid);

    UserVO selectInfoByUsername(@Param("username") String username);
    UserVO selectInfoByUid(@Param("uid") Integer uid);


    Integer updatePasswordByUid(@Param("uid") Integer uid, @Param("password") String encodeNewPwd);
}
