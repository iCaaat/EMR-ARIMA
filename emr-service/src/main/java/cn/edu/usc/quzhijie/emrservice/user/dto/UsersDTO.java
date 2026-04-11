package cn.edu.usc.quzhijie.emrservice.user.dto;

import cn.edu.usc.quzhijie.emrservice.common.query.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsersDTO extends PageQuery {
    private String roleCode;
    private String username;
    private String realName;
    private String idCard;
    private String phone;
    private String email;
    private LocalDateTime createTimeBegin;
    private LocalDateTime createTimeEnd;
    private LocalDateTime updateTimeBegin;
    private LocalDateTime updateTimeEnd;
}
