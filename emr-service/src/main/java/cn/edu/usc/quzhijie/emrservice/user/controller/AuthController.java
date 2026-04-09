package cn.edu.usc.quzhijie.emrservice.user.controller;

import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.common.service.RedisService;
import cn.edu.usc.quzhijie.emrservice.common.util.JwtUtils;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.service.AuthService;
import cn.edu.usc.quzhijie.emrservice.user.service.UserService;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.MenuVO;
import cn.edu.usc.quzhijie.emrservice.user.vo.RefreshVO;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Validated UserLoginDTO dto) {
        return Result.success("登录成功", authService.login(dto));
    }

    @PostMapping("/refresh")
    public Result<RefreshVO> refreshToken(@RequestParam @NotBlank(message = "refresh token cannot be empty") String refreshToken) {
        return Result.success("刷新成功", authService.refreshToken(refreshToken));
    }

    @DeleteMapping("/logout")
    public Result<String> logout(@RequestParam @NotBlank(message = "refresh token cannot be empty") String refreshToken) {
        return Result.success(authService.logout(refreshToken));
    }

    @GetMapping("/menu")
    public Result<List<MenuVO>> getMenu(Authentication authentication) {
        Claims claims = (Claims) authentication.getDetails();
        String roleCode = (String) claims.get("role");
        return Result.success(authService.getMenu(roleCode));
    }
}
