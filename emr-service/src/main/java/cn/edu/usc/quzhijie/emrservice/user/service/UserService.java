package cn.edu.usc.quzhijie.emrservice.user.service;

import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.user.dto.*;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.RegisterDoctorVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UsersVO;

import java.util.List;

public interface UserService {


    /**
     * 注册
     * @param dto
     * @return
     */
    String register(PatientRegisterDTO dto);

    /**
     * 查询用户信息
     */
    UserVO getUserInfo(Integer uid);

    String changePassword(UserChangePasswordDTO dto);

    Boolean checkUsernameExists(String username);

    String updateUserInfo(UpdateUserDTO dto);

    PageResult<UsersVO> getUsers(UsersDTO dto);

    RegisterDoctorVO registerDoctor(DoctorRegisterDTO dto);
}
