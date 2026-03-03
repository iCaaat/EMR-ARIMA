package cn.edu.usc.quzhijie.emrservice.user.service;

import cn.edu.usc.quzhijie.emrservice.user.dto.UserChangePasswordDTO;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserRegisterDTO;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;

public interface UserService {
    /**
     * 登录
     * @param dto
     * @return
     */
    LoginVO login(UserLoginDTO dto);

    /**
     * 注册
     * @param dto
     * @return
     */
    String register(UserRegisterDTO dto);

    /**
     * 查询用户信息
     */
    UserVO getUserInfo(Integer uid);

    String changePassword(UserChangePasswordDTO dto);
}
