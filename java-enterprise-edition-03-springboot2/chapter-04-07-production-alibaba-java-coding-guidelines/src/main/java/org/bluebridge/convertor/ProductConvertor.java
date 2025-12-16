package org.bluebridge.convertor;

import org.bluebridge.dto.PatchProductDTO;
import org.bluebridge.dto.UpdateProductDTO;
import org.bluebridge.entity.ProductDO;
import org.bluebridge.dto.CreateProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author lingwh
 * @desc 商品对象映射器
 * @date 2025/12/13 11:20
 */
@Mapper(componentModel = "spring")
public interface ProductConvertor {

    ProductConvertor INSTANCE = Mappers.getMapper(ProductConvertor.class);

    /**
     * 将 CreateProductDTO 转换为 Product 实体
     * @param createProductDTO 创建商品 DTO
     * @return Product 实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "1")
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted", constant = "0")
    ProductDO toProductDO(CreateProductDTO createProductDTO);

    /**
     * 将 UpdateProductDTO 转换为 Product 实体
     * @param updateProductDTO 创建商品 DTO
     * @return ProductDO 实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "1")
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted", constant = "0")
    ProductDO toProductDO(UpdateProductDTO updateProductDTO);

    /**
     * 将 PatchProductDTO 转换为 Product 实体
     * @param patchProductDTO 创建商品 DTO
     * @return ProductDO 实体
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "stock", ignore = true)
    @Mapping(target = "status", constant = "1")
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted", constant = "0")
    ProductDO toProductDO(PatchProductDTO patchProductDTO);

    /**
     * 将 Product 实体转换为 CreateProductDTO
     * @param product 商品实体
     * @return CreateProductDTO 创建商品 DTO
     */
    //@Mapping(target = "id", ignore = true)
    //CreateProductDTO toCreateProductDTO(Product product);
    /**
     * 将 UpdateProductDTO 更新到 Product 实体
     * @param updateProductDTO 更新商品 DTO
     * @param product 商品实体
     */
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "status", ignore = true)
//    @Mapping(target = "createTime", ignore = true)
//    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
//    @Mapping(target = "isDeleted", ignore = true)
//    void updateProductFromDto(UpdateProductDTO updateProductDTO, @MappingTarget Product product);
    
    /**
     * 将 PatchProductDTO 更新到 Product 实体
     * @param patchProductDTO 部分更新商品 DTO
     * @param product 商品实体
     */
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createTime", ignore = true)
//    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
//    @Mapping(target = "isDeleted", ignore = true)
//    void updateProductFromPatchDto(PatchProductDTO patchProductDTO, @MappingTarget Product product);

    /**
     * 将 Product 实体转换为 ProductVO
     * @param product 商品实体
     * @return ProductVO 商品视图对象
     */
//    ProductVO toProductVO(Product product);

    /**
     * 将 Product 实体更新到 ProductVO
     * @param product 商品实体
     * @param productVO 商品视图对象
     */
//    void updateProductVOFromProduct(Product product, @MappingTarget ProductVO productVO);

}