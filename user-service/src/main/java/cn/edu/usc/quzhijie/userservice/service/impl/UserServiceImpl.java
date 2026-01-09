package cn.edu.usc.quzhijie.userservice.service.impl;


import cn.edu.usc.quzhijie.userservice.converter.UserBaseConverter;
import cn.edu.usc.quzhijie.userservice.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.userservice.entity.UserBase;
import cn.edu.usc.quzhijie.userservice.exception.BizException;
import cn.edu.usc.quzhijie.userservice.mapper.UserMapper;
import cn.edu.usc.quzhijie.userservice.service.UserService;
import cn.edu.usc.quzhijie.userservice.vo.UserVO;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final UserBaseConverter userBaseConverter;

    @Override
    public List<UserVO> login(UserLoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        if (StringUtils.isBlank(username)) {
            throw new BizException("用户名不能为空");
        }

        List<UserBase> user = userMapper.getByUsername(username);

        if (user.isEmpty()) {
            throw new BizException("用户不存在");
        }

        return userBaseConverter.toVOList(user);
    }
}
