package org.bluebridge.convertor;

import org.bluebridge.dto.ProductPatchDTO;
import org.bluebridge.dto.ProductCreateDTO;
import org.bluebridge.dto.ProductQueryDTO;
import org.bluebridge.dto.ProductUpdateDTO;
import org.bluebridge.domain.ProductDO;
import org.bluebridge.vo.ProductVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lingwh
 * @desc 商品对象映射器
 * @date 2025/12/13 11:20
 */
@Mapper
public interface ProductConvertor {

    ProductConvertor INSTANCE = Mappers.getMapper(ProductConvertor.class);

    /**
     * 将 CreateProductDTO 转换为 ProductDO 实体
     * @param createProductDTO 创建商品 DTO
     * @return ProductDO 实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "1")
    ProductDO toProductDO(ProductCreateDTO createProductDTO);

    /**
     * 将 QueryProductDTO 转换为 ProductDO 实体
     * @param queryProductDTO 查询商品 DTO
     * @return ProductDO 实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", ignore = true) // 忽略price字段，因为查询时使用minPrice和maxPrice
    ProductDO toProductDO(ProductQueryDTO queryProductDTO);

    /**
     * 将 UpdateProductDTO 转换为 ProductDO 实体
     * @param updateProductDTO 更新商品 DTO
     * @return ProductDO 实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "1")
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
     * 将 ProductCreateDTO 列表转换为 ProductDO 列表
     * @param productCreateDTOList
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "1")
    List<ProductDO> toProductDOList(List<ProductCreateDTO> productCreateDTOList);

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
    @Mapping(target = "isDeleted", ignore = true)
    List<ProductVO> toProductVOList(List<ProductDO> productDOList);

}