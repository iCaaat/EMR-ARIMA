package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDoctorVO {
    private Integer doctorId;
    private String realName;
    private String idCard;
    private String username;
    private String password;
}
