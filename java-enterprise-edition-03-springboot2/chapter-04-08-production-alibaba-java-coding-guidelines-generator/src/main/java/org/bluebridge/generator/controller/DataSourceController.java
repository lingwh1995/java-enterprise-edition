package org.bluebridge.generator.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.pagehelper.PageInfo;
import org.bluebridge.common.enums.OperationTypeEnum;
import org.bluebridge.common.domain.query.PageQuery;
import org.bluebridge.common.domain.query.Query;
import org.bluebridge.common.domain.query.Sort;
import org.bluebridge.common.domain.response.Result;
import org.bluebridge.common.util.SortUtils;
import org.bluebridge.generator.domain.dto.DataSourceQueryDTO;
import org.bluebridge.generator.domain.dto.DataSourceUpdateDTO;
import org.bluebridge.generator.domain.entity.DataSourceMetaData;
import org.bluebridge.generator.domain.dto.DataSourceCreateDTO;
import org.bluebridge.generator.service.DataSourceService;
import org.bluebridge.generator.domain.vo.DataSourceVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/23 12:36
 */
@RestController
@RequestMapping("/api/v1/data-sources")
public class DataSourceController {

    @Resource
    private DataSourceService dataSourceService;

    /**
     * 新增数据源
     * @param dataSourceCreateDTO
     * @return
     */
    @PostMapping
    public Result<Integer> createDataSource(@RequestBody @Valid DataSourceCreateDTO dataSourceCreateDTO) {
        int rows = dataSourceService.createDataSource(dataSourceCreateDTO);
        return Result.buildRowsResult(rows, OperationTypeEnum.CREATE);
    }

    /**
     * 根据ID删除数据源
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteDataSourceById(
            @PathVariable @NotNull(message = "数据源ID不能为空")
            @Min(value = 1, message = "数据源ID必须大于0") Integer id) {
        int rows = dataSourceService.deleteDataSourceById(id);
        return Result.buildRowsResult(rows, OperationTypeEnum.DELETE);
    }

    /**
     * 更新数据源
     * @param dataSourceUpdateDTO
     * @return
     */
    @PutMapping("/{id}")
    public Result<Integer> updateDataSource(
            @PathVariable @NotNull(message = "数据源ID不能为空")
            @Min(value = 1, message = "数据源ID必须大于0") Integer id,
            @Valid @RequestBody DataSourceUpdateDTO dataSourceUpdateDTO) {
        int rows = dataSourceService.updateDataSource(id, dataSourceUpdateDTO);
        return Result.buildRowsResult(rows, OperationTypeEnum.UPDATE);
    }

    /**
     *  根据ID查询数据源
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DataSourceVO> getDataSourceById(
            @PathVariable @NotNull(message = "数据源ID不能为空")
            @Min(value = 1, message = "数据源ID必须大于0") Integer id) {
        DataSourceVO dataSourceVO = dataSourceService.getDataSourceById(id);
        return Result.buildDataResult(dataSourceVO, OperationTypeEnum.QUERY_ONE);
    }

    /**
     * 获取所有数据源列表
     * @return
     */
    @GetMapping
    public Result<List<DataSourceVO>> listDataSource() {
        List<DataSourceVO> dataSourceVOList = dataSourceService.listDataSource();
        return Result.buildDataResult(dataSourceVOList, OperationTypeEnum.QUERY_LIST);
    }

    /**
     * 按条件查询数据源列表
     * @param name
     * @param orderBy
     * @param order
     * @return
     */
    @GetMapping("/search")
    public Result<List<DataSourceVO>> searchDataSource(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "create_time") @Pattern(regexp = "create_time") String orderBy,
            @RequestParam(required = false, defaultValue = "desc") @Pattern(regexp = "asc|desc") String order) {
        // 构建排序条件列表
        List<Sort> sortList = SortUtils.toSortList(orderBy, order);

        // 构建查询参数
        DataSourceQueryDTO dataSourceQueryDTO = DataSourceQueryDTO.builder()
                .name(name)
                .type(type)
                .build();

        // 构建排序查询参数
        Query<DataSourceQueryDTO> query = Query.<DataSourceQueryDTO>builder()
                .conditions(dataSourceQueryDTO)
                .sortList(sortList)
                .build();

        List<DataSourceVO> dataSourceVOList = dataSourceService.searchDataSource(query);
        return Result.buildDataResult(dataSourceVOList, OperationTypeEnum.QUERY_LIST);
    }

    /**
     * 分页查询-获取数据源列表
     * @param name
     * @param type
     * @param orderBy
     * @param order
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<PageInfo<DataSourceVO>> pageDataSource(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "create_time") @Pattern(regexp = "create_time") String orderBy,
            @RequestParam(required = false, defaultValue = "desc") @Pattern(regexp = "asc|desc") String order,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "页码必须大于0") Integer pageNum,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页数量必须大于0") Integer pageSize) {
        // 构建排序条件列表
        List<Sort> sortList = SortUtils.toSortList(orderBy, order);

        // 构建查询参数
        DataSourceQueryDTO dataSourceQueryDTO = DataSourceQueryDTO.builder()
                .name(name)
                .type(type)
                .build();

        // 构建分页排序参数
        PageQuery<DataSourceQueryDTO> pageQuery = PageQuery.<DataSourceQueryDTO>builder()
                .conditions(dataSourceQueryDTO)
                .sortList(sortList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();

        PageInfo<DataSourceVO> pageInfo = dataSourceService.page(pageQuery);
        return Result.buildDataResult(pageInfo, OperationTypeEnum.QUERY_PAGE);
    }

    /**
     * 根据数据源ID获取数据库元信息
     * @param id
     * @return
     */
    @SaCheckLogin
    @GetMapping("/meta-data/{id}")
    public Result<DataSourceMetaData> getDatabaseMetaDataById(
            @PathVariable @NotNull(message = "数据源ID不能为空")
            @Min(value = 1, message = "数据源ID必须大于0") Integer id) {
        DataSourceMetaData databaseMetaData = null;
        try {
            databaseMetaData = dataSourceService.getDataSourceMetaDataById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.buildDataResult(databaseMetaData, OperationTypeEnum.QUERY_ONE);
    }

}