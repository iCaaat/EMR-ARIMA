package cn.edu.usc.quzhijie.emrservice.file.service;

import cn.edu.usc.quzhijie.emrservice.file.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileInfo upload(MultipartFile file) throws IOException;
    void delete(Long filedId);
}
