package cn.edu.usc.quzhijie.emrservice.user.service.impl;


import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.common.util.JwtUtils;
import cn.edu.usc.quzhijie.emrservice.user.converter.UserBaseConverter;
import cn.edu.usc.quzhijie.emrservice.user.dto.PatientRegisterDTO;
import cn.edu.usc.quzhijie.emrservice.user.dto.UpdateUserDTO;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserChangePasswordDTO;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.entity.Role;
import cn.edu.usc.quzhijie.emrservice.user.entity.UserBase;
import cn.edu.usc.quzhijie.emrservice.user.entity.UserRole;
import cn.edu.usc.quzhijie.emrservice.user.mapper.UserMapper;
import cn.edu.usc.quzhijie.emrservice.user.service.UserService;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    private final UserBaseConverter userBaseConverter;

    private final JwtUtils jwtUtils;

    /**
     * 登录
     * @param dto
     * @return
     */
    @Override
    public LoginVO login(UserLoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        // 1.查用户
        UserBase user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        // 2.查角色
        Integer uid = user.getUid();
        Role role = userMapper.selectRoleByUid(uid);
        String roleCode = role.getRoleCode();
        String roleName = role.getRoleName();

        // 3.验证密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(password, user.getPassword());
        if (!matches) {
            throw new BizException("用户名或密码错误");
        }

        // 4.生成token
        Map<String, Object> claim = new HashMap<>();
        claim.put("uid", uid);
        claim.put("role", roleCode);
        String token = jwtUtils.generateToken(user.getUsername(), claim);

        // 6.处理成VO
        LoginVO loginVO = userBaseConverter.toVO(user);
        loginVO.setToken(token);
        loginVO.setRoleName(roleName);

        return loginVO;
    }

    /**
     * 注册
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public String register(PatientRegisterDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String realName = dto.getRealName();
        String idCard = dto.getIdCard();

        // 1.业务校验
        if (userMapper.checkUsernameExists(username)) {
            throw new BizException("用户名已被注册");
        }
        if (userMapper.checkRealNameAndIdCardExists(realName, idCard)) {
            throw new BizException("此身份证信息已存在账户");
        }

        // 2.密码加密存储
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePwd = encoder.encode(password);

        // 3.保存用户
        UserBase user = new UserBase();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(encodePwd);
        userMapper.insertUserBase(user);

        Integer uid = user.getUid();
        Role role = userMapper.selectRoleByRoleCode(dto.getRoleCode());
        Integer roleId = role.getRoleId();
        UserRole userRole = new UserRole();
        userRole.setUid(uid);
        userRole.setRoleId(roleId);
        userMapper.insertUserRole(userRole);

        return "注册成功";
    }

    /**
     * 查询基本信息
     * @return UserVO
     */
    @Override
    public UserVO getUserInfo(Integer uid) {
        return userMapper.selectInfoByUid(uid);
    }


    @Override
    public String changePassword(UserChangePasswordDTO dto) {
        Integer uid = dto.getUid();
        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        String confirmPassword = dto.getConfirmNewPassword();

        if (oldPassword.equals(newPassword)) {
            throw new BizException("新旧密码不能相同");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new BizException("新密码和确认密码不一致");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserBase user = userMapper.selectByUid(uid);
        boolean matches = encoder.matches(oldPassword, user.getPassword());
        if (!matches) {
            throw new BizException("当前密码错误");
        }

        String encodeNewPwd = encoder.encode(newPassword);
        Integer res = userMapper.updatePasswordByUid(uid, encodeNewPwd);
        if (res <= 0) {
            throw new BizException("密码修改失败");
        }
        return "修改密码成功,请重新登录!";
    }

    @Override
    public String updateUserInfo(UpdateUserDTO dto) {
        Integer uid = dto.getUid();
        UserBase user = userMapper.selectByUid(uid);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        userMapper.updateUserByUid(dto);
        return "修改个人信息成功";
    }

    @Override
    public Boolean checkUsernameExists(String username) {
        return userMapper.checkUsernameExists(username);
    }
}
