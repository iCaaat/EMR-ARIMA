package cn.edu.usc.quzhijie.emrservice.user.service.impl;


import cn.edu.usc.quzhijie.emrservice.user.converter.UserBaseConverter;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserRegisterDTO;
import cn.edu.usc.quzhijie.emrservice.user.entity.Role;
import cn.edu.usc.quzhijie.emrservice.user.entity.UserBase;
import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.user.mapper.UserMapper;
import cn.edu.usc.quzhijie.emrservice.user.service.UserService;
import cn.edu.usc.quzhijie.emrservice.common.util.JwtUtils;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        // 1.参数简单校验
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new BizException("用户名或密码不能为空");
        }

        // 2.查用户
        UserBase user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        // 3.查角色
        Integer uid = user.getUid();
        Role role = userMapper.selectRoleByUid(uid);
        String roleCode = role.getRoleCode();
        String roleName = role.getRoleName();

        // 4.验证密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(password, user.getPassword());
        if (!matches) {
            throw new BizException("用户名或密码错误");
        }

        // 5.生成token
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
    public String register(UserRegisterDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new BizException("用户名或密码不能为空");
        }

        if (password.length() < 6 || password.length() > 20) {
            throw new BizException("密码长度应在6-20个字符之间");
        }

        if (username.length() < 3 || username.length() > 20) {
            throw new BizException("用户名长度应在3-20个字符之间");
        }

        if (!userVerify(dto)) {
            throw new BizException("用户信息格式不正确");
        }

        // 密码加密存储
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePwd = encoder.encode(password);


        return "";
    }

    /**
     * 查询基本信息
     * @return UserVO
     */
    @Override
    public UserVO getUserInfo(String username) {
        return userMapper.selectInfoByUsername(username);
    }

    /**
     * 用户基本信息输入校验
     */
    public boolean userVerify(UserRegisterDTO dto) {
        // 校验规则
        // 真实姓名: 2-18个汉字或包含空格的64个字母以内
        String realNameRegex = "(^[\\u4e00-\\u9fa5]{2,18}$)|(^[a-zA-Z\\s]{1,64}$)";
        // 手机号：11位数字，以1开头
        String phoneRegex = "^1\\d{10}$";
        // 邮箱：详细校验
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        // 角色编码: ADMIN/DOCTOR/PATIENT
        String roleCodeRegex = "^(ADMIN|DOCTOR|PATIENT)$";

        String realName = dto.getRealName();
        Character gender = dto.getGender();
        String phone = dto.getPhone();
        String email = dto.getEmail();
        String roleCode = dto.getRoleCode();

        if (!StringUtils.isBlank(realName) && !realName.matches(realNameRegex)) {
            throw new BizException("真实姓名格式不正确");
        }

        if (gender != 'M' && gender != 'F' && gender != 'U') {
            throw new BizException("性别格式不正确");
        }

        if (!StringUtils.isBlank(phone) && !phone.matches(phoneRegex)) {
            throw new BizException("手机号格式不正确");
        }

        if (!StringUtils.isBlank(email) && !email.matches(emailRegex)) {
            throw new BizException("邮箱格式不正确");
        }

        if (!roleCode.matches(roleCodeRegex)) {
            throw new BizException("角色编码格式不正确");
        }

        return true;
    }


}
