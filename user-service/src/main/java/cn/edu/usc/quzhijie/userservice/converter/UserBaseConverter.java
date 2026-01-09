package cn.edu.usc.quzhijie.userservice.converter;

import cn.edu.usc.quzhijie.userservice.entity.UserBase;
import cn.edu.usc.quzhijie.userservice.vo.UserVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserBaseConverter {
    UserVO toVO(UserBase userBase);

    List<UserVO> toVOList(List<UserBase> entityList);
}
