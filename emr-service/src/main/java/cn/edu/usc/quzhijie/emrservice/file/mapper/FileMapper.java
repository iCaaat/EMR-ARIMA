package cn.edu.usc.quzhijie.emrservice.file.mapper;

import cn.edu.usc.quzhijie.emrservice.file.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileMapper {

    void insert(FileInfo fileInfo);


    FileInfo selectById(@Param("id") Long fileId);


    void deleteById(@Param("id") Long fileId);
}
