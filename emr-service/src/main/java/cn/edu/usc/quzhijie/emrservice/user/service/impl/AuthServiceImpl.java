package cn.edu.usc.quzhijie.emrservice.user.service.impl;

import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.common.exception.InvalidTokenException;
import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.common.service.RedisService;
import cn.edu.usc.quzhijie.emrservice.common.util.JwtUtils;
import cn.edu.usc.quzhijie.emrservice.user.converter.UserBaseConverter;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.entity.Role;
import cn.edu.usc.quzhijie.emrservice.user.entity.UserBase;
import cn.edu.usc.quzhijie.emrservice.user.mapper.UserMapper;
import cn.edu.usc.quzhijie.emrservice.user.service.AuthService;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtils jwtUtils;
    private final RedisService redisService;
    private final UserMapper userMapper;
    private final UserBaseConverter userBaseConverter;

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
        if (role == null) {
            throw new BizException("用户未分配角色，请联系管理员处理");
        }
        String roleCode = role.getRoleCode();
        String roleName = role.getRoleName();

        // 3.验证密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new BizException("用户名或密码错误");
        }

        // 4.生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", uid);
        claims.put("role", roleCode);
        String accessToken = jwtUtils.generateAccessToken(user.getUsername(), claims);
        String refreshToken = jwtUtils.generateRefreshToken(user.getUsername(), claims);

        // 5.Refresh Token 存入Redis
        Claims refreshClaims = jwtUtils.parseToken(refreshToken);
        String jti = refreshClaims.getId();
        long ttl = refreshClaims.getExpiration().getTime() - System.currentTimeMillis();
        redisService.set("jwt:refresh:" + jti, user.getUsername(), ttl, TimeUnit.MILLISECONDS);


        // 6.处理成VO
        LoginVO loginVO = userBaseConverter.toVO(user);
        loginVO.setAccessToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        loginVO.setRoleName(roleName);

        return loginVO;
    }

    @Override
    public String refreshToken(String refreshToken) {
        // 验证刷新令牌是否有效
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new InvalidTokenException("invalid refresh token");
        }

        // 验证token是否是刷新令牌
        Claims claims = jwtUtils.parseToken(refreshToken);
        String type = (String) claims.get("type");
        if (!JwtUtils.REFRESH.equals(type)) {
            throw new InvalidTokenException("not refresh token");
        }

        // 解析用户名
        String username = claims.getSubject();

        // 解析Redis中是否有对应的刷新令牌
        String jti = claims.getId();
        String storedJti = (String) redisService.get("jwt:refresh:" + jti);
        if (storedJti == null || !storedJti.equals(username)) {
            throw new InvalidTokenException("refresh token invalid");
        }

        // 生成Access Token
        Integer uid = (Integer) claims.get("uid");
        String role = (String) claims.get("role");
        Map<String, Object> claim = new HashMap<>();
        claim.put("uid", uid);
        claim.put("role", role);

        return jwtUtils.generateAccessToken(username, claim);
    }
}
