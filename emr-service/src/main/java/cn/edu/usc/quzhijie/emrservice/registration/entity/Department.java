package cn.edu.usc.quzhijie.emrservice.registration.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Department {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer sort;
    private LocalDateTime createTime;
}
