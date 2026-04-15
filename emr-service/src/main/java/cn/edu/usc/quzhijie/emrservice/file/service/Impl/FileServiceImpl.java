package cn.edu.usc.quzhijie.emrservice.file.service.Impl;

import cn.edu.usc.quzhijie.emrservice.common.config.FileProperties;
import cn.edu.usc.quzhijie.emrservice.common.exception.BizException;
import cn.edu.usc.quzhijie.emrservice.file.entity.FileInfo;
import cn.edu.usc.quzhijie.emrservice.file.mapper.FileMapper;
import cn.edu.usc.quzhijie.emrservice.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileProperties fileProperties;
    private final FileMapper fileMapper;

    @Override
    public FileInfo upload(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();

        String suffix = originalName.substring(originalName.lastIndexOf("."));

        String fileName = UUID.randomUUID() + suffix;

        String relativePath = "avatar/" + LocalDate.now() + "/" + fileName;

        File dest = new File(fileProperties.getBaseDir(), relativePath);

        dest.getParentFile().mkdirs();

        file.transferTo(dest);

        // 保存数据库
        FileInfo fileInfo = new FileInfo();
        fileInfo.setOriginalName(originalName);
        fileInfo.setFileName(fileName);
        fileInfo.setFilePath(relativePath);
        fileInfo.setFileSize(String.valueOf(file.getSize()));
        fileInfo.setFileType(suffix);
        fileInfo.setCreateTime(LocalDateTime.now());

        fileMapper.insert(fileInfo);

        return fileInfo;
    }

    @Override
    @Transactional
    public void delete(Long fileId) {
        FileInfo fileInfo = fileMapper.selectById(fileId);

        if (fileInfo == null) {
            throw new BizException("文件不存在");
        }

        File file = new File(fileProperties.getBaseDir(), fileInfo.getFilePath());

        if (file.exists()) {
            file.delete();
        }

        fileMapper.deleteById(fileId);
    }
}
