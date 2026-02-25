package cn.edu.usc.quzhijie.emrservice.user.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserRegisterDTO;
import cn.edu.usc.quzhijie.emrservice.user.service.UserService;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody UserLoginDTO dto) {
        return Result.success("登录成功", userService.login(dto));
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterDTO dto) {
        return Result.success("注册成功!", userService.register(dto));
    }

    /**
     * 注销登录
     */


    /**
     * 获取个人基本信息+详细信息
     */
    @GetMapping("/me")
    public Result<UserVO> getUserInfo(Authentication authentication) {
        String username = authentication.getName();
        return Result.success("查询成功", userService.getUserInfo(username));
    }


}
