package cn.edu.usc.quzhijie.emrservice.user.controller;

import cn.edu.usc.quzhijie.emrservice.common.result.Result;
import cn.edu.usc.quzhijie.emrservice.common.service.RedisService;
import cn.edu.usc.quzhijie.emrservice.common.util.JwtUtils;
import cn.edu.usc.quzhijie.emrservice.user.dto.UserLoginDTO;
import cn.edu.usc.quzhijie.emrservice.user.service.AuthService;
import cn.edu.usc.quzhijie.emrservice.user.service.UserService;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
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
    public ResponseEntity<Result<String>> refreshToken(@RequestParam String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.fail(400, "refresh token cannot be empty"));
        }

        // 刷新令牌有效，生成新的访问令牌
        String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(Result.success("刷新成功", accessToken));
    }
}
