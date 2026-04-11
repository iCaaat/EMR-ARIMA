package cn.edu.usc.quzhijie.emrservice.user.service.impl;


import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.common.util.JwtUtils;
import cn.edu.usc.quzhijie.emrservice.user.converter.UserBaseConverter;
import cn.edu.usc.quzhijie.emrservice.user.dto.*;
import cn.edu.usc.quzhijie.emrservice.user.entity.Role;
import cn.edu.usc.quzhijie.emrservice.user.entity.UserBase;
import cn.edu.usc.quzhijie.emrservice.user.entity.UserRole;
import cn.edu.usc.quzhijie.emrservice.user.mapper.UserMapper;
import cn.edu.usc.quzhijie.emrservice.user.service.UserService;
import cn.edu.usc.quzhijie.emrservice.user.util.InfoUtils;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UsersVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;



    private final JwtUtils jwtUtils;

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

        // 4.插入默认就诊人信息
        // 身份证计算出生日期和性别
        String gender = idCard.charAt(idCard.length() - 2) % 2 == 0 ? "F" : "M";
        LocalDate birthday = LocalDate.parse(idCard.substring(6, 14), DateTimeFormatter.ofPattern("yyyyMMdd"));
        dto.setGender(gender);
        dto.setBirthday(birthday);
        userMapper.insertPatient(uid, dto);

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

    @Override
    public PageResult<UsersVO> getUsers(UsersDTO dto) {
        Integer pageNum = dto.getPageNum();
        Integer pageSize = dto.getPageSize();

        Long total = userMapper.countUserByCondition(dto);
        if (total == 0) {
            return new PageResult<>(0L, pageNum, pageSize, List.of());
        }
        List<UsersVO> users = userMapper.listUserByCondition(dto);

        for (UsersVO user : users) {
            String realNameSecret = InfoUtils.maskName(user.getRealName());
            String idCardSecret = InfoUtils.maskIdCard(user.getIdCard());
            user.setRealNameSecret(realNameSecret);
            user.setIdCardSecret(idCardSecret);

            if (!StringUtils.hasText(user.getEmail())) {
                user.setEmail("未填写");
            }
        }

        return new PageResult<>(total, pageNum, pageSize, users);
    }
}
