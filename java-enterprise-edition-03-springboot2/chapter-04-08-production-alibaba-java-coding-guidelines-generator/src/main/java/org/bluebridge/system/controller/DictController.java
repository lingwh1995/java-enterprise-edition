package org.bluebridge.system.controller;

import org.bluebridge.common.domain.response.Result;
import org.bluebridge.common.enums.OperationTypeEnum;
import org.bluebridge.system.domain.vo.DictVO;
import org.bluebridge.system.service.DictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/10 14:30
 */
@RestController
@RequestMapping("/api/v1/dicts")
public class DictController {

    @Resource
    private DictService dictService;

    /**
     * http://localhost:8080/p3c-mybatis-common/api/v1/dicts/DATABASE_TYPE
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

}
