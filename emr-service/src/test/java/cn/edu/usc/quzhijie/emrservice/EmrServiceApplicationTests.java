package cn.edu.usc.quzhijie.emrservice;

import cn.edu.usc.quzhijie.emrservice.common.config.JwtProperties;
import cn.edu.usc.quzhijie.emrservice.common.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;

@SpringBootTest
class EmrServiceApplicationTests {

    /**
     * 生成随机的base64编码（32字节长度）
     */
    @Test
    void keyGen() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(base64Key);
    }

    @Test
    void generateToken() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret("EUqxSvI4extlwRUeCDNBVq4p6SIp1JSyK7hTXAbvE7M=");
        jwtProperties.setExpiration(3600*1000);

        JwtUtils jwtUtils = new JwtUtils(jwtProperties);
        String token = jwtUtils.generateToken("8888", "8888");
        System.out.println(token);
    }

    @Test
    void parseToken() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret("EUqxSvI4extlwRUeCDNBVq4p6SIp1JSyK7hTXAbvE7M=");
        jwtProperties.setExpiration(3600*1000);

        JwtUtils jwtUtils = new JwtUtils(jwtProperties);
        Claims claims = jwtUtils.parseToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4ODg4Iiwicm9sZSI6Ijg4ODgiLCJpYXQiOjE3NzE4NDU2MzYsImV4cCI6MTc3MTg0OTIzNn0.7uVq0LICDzjT6V26f0xGJebqBn_IxKgsL1m8HCjrFpo");
        System.out.println(true);
    }

    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("8888");
        System.out.println(encode);
    }

}
