package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Data;

@Data
public class RefreshVO {
    private String accessToken;
    private String refreshToken;
}
