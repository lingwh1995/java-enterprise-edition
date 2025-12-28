package org.bluebridge.security.converter;

import org.bluebridge.security.domain.UserCreatDTO;
import org.bluebridge.security.domain.UserDO;
import org.bluebridge.security.domain.UserLoginDTO;
import org.mapstruct.Mapper;

/**
 * @author lingwh
 * @desc 商品对象映射器
 * @date 2025/12/13 11:20
 */
// 组件模型设置为Spring，使MapStruct生成的实现类可以被Spring管理
@Mapper(componentModel = "spring")
public interface UserConverter {

    /**
     * 将 CreateProductDTO 转换为 DataSourceDO 实体
     * @param userDO
     * @return
     */
    UserCreatDTO toUserCreatDTO(UserDO userDO);

    /**
     * 将 UserLoginDTO 转换为 UserDO
     * @param userLoginDTO
     * @return
     */
    UserDO toUserDO(UserLoginDTO userLoginDTO);

}