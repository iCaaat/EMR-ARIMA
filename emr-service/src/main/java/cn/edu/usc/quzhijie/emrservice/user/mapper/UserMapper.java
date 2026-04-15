package cn.edu.usc.quzhijie.emrservice.user.mapper;

import cn.edu.usc.quzhijie.emrservice.user.dto.*;
import cn.edu.usc.quzhijie.emrservice.common.entity.DoctorExp;
import cn.edu.usc.quzhijie.emrservice.common.entity.Role;
import cn.edu.usc.quzhijie.emrservice.common.entity.UserBase;
import cn.edu.usc.quzhijie.emrservice.common.entity.UserRole;
import cn.edu.usc.quzhijie.emrservice.user.vo.DoctorSimpleVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.MenuVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UsersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    // 用户名查询用户基本信息
    UserBase selectByUsername(@Param("username") String username);
    // uid查询用户基本信息
    UserBase selectByUid(@Param("uid") Integer uid);

    // uid查询用户角色信息
    Role selectRoleByUid(@Param("uid") Integer uid);

    // 用户名查询用户视图信息
    UserVO selectInfoByUsername(@Param("username") String username);
    // uid查询用户视图信息
    UserVO selectInfoByUid(@Param("uid") Integer uid);

    // 角色代码查询角色信息
    Role selectRoleByRoleCode(@Param("roleCode") String roleCode);


    // uid修改用户密码
    Integer updatePasswordByUid(@Param("uid") Integer uid, @Param("password") String encodeNewPwd);

    // 检查用户名是否存在
    Boolean checkUsernameExists(@Param("username") String username);
    // 检查身份证号和姓名是否存在
    Boolean checkRealNameAndIdCardExists(@Param("realName") String realName, @Param("idCard") String idCard);

    // 插入用户基本信息
    Integer insertUserBase(UserBase user);

    // 插入用户角色信息
    Integer insertUserRole(UserRole userRole);

    Integer updateUserByUid(UpdateUserDTO dto);

    List<MenuVO> listMenusByRoleCode(String roleCode);

    Integer insertPatient(@Param("belongingUid") Integer uid, @Param("patient") PatientRegisterDTO dto);

    List<UsersVO> listUserByCondition(UsersDTO dto);
    Long countUserByCondition(UsersDTO dto);

    Integer insertDoctor(@Param("uid") Integer uid, @Param("doctor") DoctorRegisterDTO dto);

    DoctorExp selectDoctorById(@Param("doctorId") Integer doctorId);

    Long countDoctorSimpleByCondition(DoctorSimpleDTO dto);
    List<DoctorSimpleVO> listDoctorSimpleByCondition(DoctorSimpleDTO dto);

    List<DoctorExp> selectDoctorByIds(@Param("list") List<Integer> doctorIds);
}
