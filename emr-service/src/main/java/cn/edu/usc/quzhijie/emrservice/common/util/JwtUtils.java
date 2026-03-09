package cn.edu.usc.quzhijie.emrservice.common.util;


import cn.edu.usc.quzhijie.emrservice.common.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtils {
    public static final String ACCESS = "access";
    public static final String REFRESH = "refresh";

    private final long expiration;
    private final SecretKey secretKey;
    private final JwtParser jwtParser;
    private final long refreshTokenExpiration;

    public JwtUtils(JwtProperties jwtProperties) {
        this.expiration = jwtProperties.getExpiration();
        this.secretKey = Keys.hmacShaKeyFor( jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8) );
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
        this.refreshTokenExpiration = jwtProperties.getRefreshTokenExpiration();
    }

    /**
     * 生成 Token
     */
    public String generateAccessToken(String username, String role) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", ACCESS)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 生成 Token（支持自定义 claims）
     */
    public String generateAccessToken(String username, Map<String, Object> claims) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .claim("type", ACCESS)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析 Token
     */
    public Claims parseToken(String token) {
        return jwtParser
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 获取用户名
     */
    public String getUsername(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 获取指定 claim
     */
    public Object getClaim(String token, String claimKey) {
        return parseToken(token).get(claimKey);
    }

    /**
     * 判断是否过期
     */
    public boolean isExpired(String token) {
        return parseToken(token)
                .getExpiration()
                .before(new Date());
    }

    /**
     * 校验 Token 是否合法
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 生成 Refresh Token
     */
    public String generateRefreshToken(String username, String role) {
        String jti = UUID.randomUUID().toString();

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .id(jti)
                .subject(username)
                .claim("type", REFRESH)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String username, Map<String, Object> claims) {
        String jti = UUID.randomUUID().toString();

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .id(jti)
                .subject(username)
                .claims(claims)
                .claim("type", REFRESH)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();

    }
}
