package cn.edu.usc.quzhijie.userservice.controller;

import cn.edu.usc.quzhijie.userservice.common.result.Result;
import cn.edu.usc.quzhijie.userservice.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.userservice.dto.UserRegisterDTO;
import cn.edu.usc.quzhijie.userservice.service.UserService;
import cn.edu.usc.quzhijie.userservice.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<List<UserVO>> login(@RequestBody UserLoginDTO dto) {
        return Result.success("登录成功!", userService.login(dto));
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterDTO dto) {
        return Result.success("注册成功!", userService.register(dto));
    }




}
