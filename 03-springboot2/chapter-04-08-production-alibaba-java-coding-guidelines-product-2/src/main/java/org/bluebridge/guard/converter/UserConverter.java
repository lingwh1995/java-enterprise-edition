package org.bluebridge.guard.converter;
import org.bluebridge.guard.domain.dto.UserCreatDTO;
import org.bluebridge.guard.domain.dto.UserLoginDTO;
import org.bluebridge.guard.domain.entity.UserDO;
import org.mapstruct.Mapper;

/**
 * 商品对象映射器
 *
 * @author lingwh
 * @date 2025/12/13 11:20
 */
// 组件模型设置为 Spring，使 MapStruct 生成的实现类可以被 Spring 管理
@Mapper(componentModel = "spring")
public interface UserConverter {

    /**
     * 将 CreateProductDTO 转换为 DataSourceDO 实体
     *
     * @param userDO
     * @return
     */
    UserCreatDTO toUserCreatDTO(UserDO userDO);

    /**
     * 将 UserLoginDTO 转换为 UserDO
     *
     * @param userLoginDTO
     * @return
     */
    UserDO toUserDO(UserLoginDTO userLoginDTO);
}