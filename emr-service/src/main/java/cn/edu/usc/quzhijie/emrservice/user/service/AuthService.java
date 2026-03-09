package cn.edu.usc.quzhijie.emrservice.user.service;

import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;

public interface AuthService {
    String refreshToken(String refreshToken);

    /**
     * 登录
     * @param dto
     * @return
     */
    LoginVO login(UserLoginDTO dto);
}
