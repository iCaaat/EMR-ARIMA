package cn.edu.usc.quzhijie.emrservice.user.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.PageResult;
import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.user.dto.*;
import cn.edu.usc.quzhijie.emrservice.user.service.UserService;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.RegisterDoctorVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UserVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.UsersVO;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody @Validated PatientRegisterDTO dto) {
        dto.setRoleCode("user");
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
    public Result<String> changePassword(Authentication authentication, @RequestBody @Validated UserChangePasswordDTO dto) {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");
        dto.setUid(uid);
        return Result.success(userService.changePassword(dto));
    }

    /**
     * 查询用户是否存在
     */
    @GetMapping("/exists")
    public Result<Boolean> checkUsernameExists(
            @RequestParam
            @NotBlank(message = "用户名不能为空")
            @Size(min = 3, max = 20, message = "用户名长度必须在3-20之间")
            String username) {
        return Result.success("查询成功", userService.checkUsernameExists(username));
    }

    /**
     * 修改个人信息
     */
    @PutMapping("/me")
    public Result<String> updateUserInfo(Authentication authentication, @RequestBody @Validated UpdateUserDTO dto)
    {
        Claims claims = (Claims) authentication.getDetails();
        Integer uid = (Integer) claims.get("uid");
        dto.setUid(uid);
        return Result.success(userService.updateUserInfo(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/search")
    public Result<PageResult<UsersVO>> getUsers(@RequestBody UsersDTO dto) {
        return Result.success(userService.getUsers(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/doctor/register")
    public Result<RegisterDoctorVO> registerDoctor(@RequestBody @Validated DoctorRegisterDTO dto) {
        dto.setRoleCode("doctor");
        RegisterDoctorVO vo = userService.registerDoctor(dto);
        String doctorName = vo.getRealName();
        return Result.success("医生：" + doctorName + "，注册成功!", vo);
    }
}
