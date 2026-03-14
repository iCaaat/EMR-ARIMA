package cn.edu.usc.quzhijie.emrservice.file.service.Impl;

import cn.edu.usc.quzhijie.emrservice.common.config.FileProperties;
import cn.edu.usc.quzhijie.emrservice.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileProperties fileProperties;

    @Override
    public String upload(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();

        String suffix = originalName.substring(originalName.lastIndexOf("."));

        String fileName = UUID.randomUUID() + suffix;

        String relativePath = "avatar/" + LocalDate.now() + "/" + fileName;

        File dest = new File(fileProperties.getBaseDir(), relativePath);

        dest.getParentFile().mkdirs();

        file.transferTo(dest);

        return relativePath;
    }

    @Override
    public void delete(Long filedId) {

    }
}
