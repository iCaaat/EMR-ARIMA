package cn.edu.usc.quzhijie.userservice.converter;

import cn.edu.usc.quzhijie.userservice.entity.UserBase;
import cn.edu.usc.quzhijie.userservice.vo.LoginVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserBaseConverter {
    LoginVO toVO(UserBase userBase);

    List<LoginVO> toVOList(List<UserBase> entityList);
}
