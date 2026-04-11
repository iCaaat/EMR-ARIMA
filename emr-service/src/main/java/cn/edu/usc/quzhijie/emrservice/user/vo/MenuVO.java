package cn.edu.usc.quzhijie.emrservice.user.vo;

import lombok.Data;

@Data
public class MenuVO {
    private Integer id;
    private String name;
    private String path;
    private String icon;
    private String roleCode;
    private String module;
    private String moduleName;
    private String sort;
}
