package cn.edu.usc.quzhijie.emrservice.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String upload(MultipartFile file) throws IOException;
    void delete(Long filedId);
}
