package cn.edu.usc.quzhijie.emrservice.file.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FileInfo {
    private Integer id;
    private String originalName;
    private String fileName;
    private String filePath;
    private String fileSize;
    private String fileType;
    private LocalDateTime createTime;
}
