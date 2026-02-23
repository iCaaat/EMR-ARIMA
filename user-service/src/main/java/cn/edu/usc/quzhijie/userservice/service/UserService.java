package cn.edu.usc.quzhijie.userservice.service;

import cn.edu.usc.quzhijie.userservice.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.userservice.dto.UserRegisterDTO;
import cn.edu.usc.quzhijie.userservice.vo.LoginVO;

import java.util.List;

public interface UserService {
    /**
     * 登录
     * @param dto
     * @return
     */
    List<LoginVO> login(UserLoginDTO dto);

    /**
     * 注册
     * @param dto
     * @return
     */
    String register(UserRegisterDTO dto);
}
