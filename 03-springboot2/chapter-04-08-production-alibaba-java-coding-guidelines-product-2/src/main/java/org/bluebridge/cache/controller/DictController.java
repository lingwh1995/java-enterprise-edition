package org.bluebridge.cache.controller;

import org.bluebridge.cache.domain.dto.DictQueryDTO;
import org.bluebridge.cache.domain.vo.DictVO;
import org.bluebridge.cache.service.DictService;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.common.domain.query.Sort;
import org.bluebridge.common.domain.response.Result;
import org.bluebridge.common.enums.OperationTypeEnum;
import org.bluebridge.common.util.SortUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/10 14:30
 */
@RestController
@RequestMapping("/dicts")
public class DictController {

    @Resource
    private DictService dictService;

    /**
     * http://localhost:8080/product/api/v1/dicts/DATABASE_TYPE
     *
     * 根据字典编码查询字典
     * @param dictCode
     * @return
     */
    @GetMapping("/{dict_code}")
    public Result<DictVO> getDictByDictCode(@PathVariable("dict_code") String dictCode) {
        DictVO dictVO = dictService.getDictByDictCode(dictCode);
        return Result.buildDataResult(dictVO, OperationTypeEnum.QUERY_ONE_CONDITION);
    }

    /**
     * 条件查询-根据查询条件获取商品列表
     * URL：/api/products
     *
     * @param dictCode 字典编码
     * @param dictName 字典名称
     * @param dictType 字典类型
     * @param orderBy 排序字段
     * @param order 排序方式
     * @return 统一响应结果
     */
    @GetMapping
    public Result<List<DictVO>> listProduct(
            @RequestParam(required = false) String dictCode,
            @RequestParam(required = false) String dictName,
            @RequestParam(required = false) Integer dictType,
            @RequestParam(required = false, defaultValue = "create_time") @Pattern(regexp = "create_time") String orderBy,
            @RequestParam(required = false, defaultValue = "desc") @Pattern(regexp = "asc|desc") String order) {

        // 构建排序条件列表
        List<Sort> sortList = SortUtils.toSortList(orderBy, order);

        // 构建查询参数
        DictQueryDTO dictQueryDTO = DictQueryDTO.builder()
                .dictCode(dictCode)
                .dictName(dictName)
                .dictType(dictType)
                .build();

        // 构建排序查询参数
        Query<DictQueryDTO> query = Query.<DictQueryDTO>builder()
                .conditions(dictQueryDTO)
                .sortList(sortList)
                .build();

        List<DictVO> productVOList = dictService.listDict(query);
        return Result.buildDataResult(productVOList, OperationTypeEnum.QUERY_LIST);
    }

}
