package cn.edu.usc.quzhijie.emrservice.user.converter;

import cn.edu.usc.quzhijie.emrservice.common.entity.UserBase;
import cn.edu.usc.quzhijie.emrservice.user.vo.LoginVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserBaseConverter {
    LoginVO toVO(UserBase userBase);

    List<LoginVO> toVOList(List<UserBase> entityList);
}
