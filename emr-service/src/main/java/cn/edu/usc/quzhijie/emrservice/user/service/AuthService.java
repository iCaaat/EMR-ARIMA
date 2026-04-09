package cn.edu.usc.quzhijie.emrservice.user.service;

import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.MenuVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.RefreshVO;

import java.util.List;

public interface AuthService {
    RefreshVO refreshToken(String refreshToken);

    /**
     * 登录
     * @param dto
     * @return
     */
    LoginVO login(UserLoginDTO dto);

    String logout(String refreshToken);

    List<MenuVO> getMenu(String roleCode);
}
