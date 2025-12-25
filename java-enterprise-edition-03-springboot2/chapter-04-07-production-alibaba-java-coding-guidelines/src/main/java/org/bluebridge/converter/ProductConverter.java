package org.bluebridge.converter;

import com.github.pagehelper.PageInfo;
import org.bluebridge.dto.ProductPatchDTO;
import org.bluebridge.dto.ProductCreateDTO;
import org.bluebridge.dto.ProductUpdateDTO;
import org.bluebridge.entity.ProductDO;
import org.bluebridge.vo.ProductVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lingwh
 * @desc 商品对象映射器
 * @date 2025/12/13 11:20
 */
// 组件模型设置为Spring，使MapStruct生成的实现类可以被Spring管理
@Mapper(componentModel = "spring")
public interface ProductConverter {

    /**
     * 将 CreateProductDTO 转换为 ProductDO 实体
     * @param createProductDTO 创建商品 DTO
     * @return ProductDO 实体
     */
    @Mapping(target = "status", constant = "1")
    ProductDO toProductDO(ProductCreateDTO createProductDTO);

    /**
     * 将 ProductCreateDTO 列表转换为 ProductDO 列表
     * @param productCreateDTOList
     * @return
     */
    List<ProductDO> toProductDOList(List<ProductCreateDTO> productCreateDTOList);

    /**
     * 将 UpdateProductDTO 转换为 ProductDO 实体
     * @param updateProductDTO 更新商品 DTO
     * @return ProductDO 实体
     */
    @Mapping(target = "id", ignore = true)
    ProductDO toProductDO(ProductUpdateDTO updateProductDTO);

    /**
     * 将 PatchProductDTO 转换为 ProductDO 实体
     * @param patchProductDTO 部分更新商品 DTO
     * @return ProductDO 实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "status", constant = "1")
    ProductDO toProductDO(ProductPatchDTO patchProductDTO);

    /**
     * 将 ProductDO 转换为 ProductVO
     * @param productDO 商品实体
     * @return ProductVO 商品视图对象
     */
    ProductVO toProductVO(ProductDO productDO);

    /**
     * 将 ProductDO 列表转换为 ProductVO 列表
     * @param productDOList 商品实体列表
     * @return ProductVO 列表
     */
    List<ProductVO> toProductVOList(List<ProductDO> productDOList);

    /**
     * 将 PageInfo<ProductDO> 转换为 PageInfo<ProductVO>
     * @param productDOPageInfo
     * @return
     */
    PageInfo<ProductVO> toProductVOPageInfo(PageInfo<ProductDO> productDOPageInfo);
}