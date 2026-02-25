package cn.edu.usc.quzhijie.emrservice.user.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Role {
    private Integer roleId;
    private String roleName;
    private String roleCode;
    private String description;
    private LocalDateTime createTime;
}
