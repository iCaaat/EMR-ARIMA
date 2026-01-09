package cn.edu.usc.quzhijie.userservice.service;

import cn.edu.usc.quzhijie.userservice.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.userservice.vo.UserVO;

import java.util.List;

public interface UserService {
    List<UserVO> login(UserLoginDTO dto);
}
