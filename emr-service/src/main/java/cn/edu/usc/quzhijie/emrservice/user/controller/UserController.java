package cn.edu.usc.quzhijie.emrservice.user.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserChangePasswordDTO;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserRegisterDTO;
import cn.edu.usc.quzhijie.emrservice.user.service.UserService;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
     * 获取个人基本信息+详细信息
     */
    @GetMapping("/me")
    public Result<UserVO> getUserInfo(Authentication authentication) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");
        return Result.success("查询成功", userService.getUserInfo(uid));
    }

    /**
     * 密码修改
     */
    @PutMapping("/password")
    public Result<String> changePassword(@RequestBody UserChangePasswordDTO dto) {
        return Result.success(userService.changePassword(dto));
    }


}
