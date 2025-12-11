package org.bluebridge.service.impl;

import org.bluebridge.entity.QueryWrapper;
import org.bluebridge.mapper.BaseMapper;
import org.bluebridge.service.IBaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/10 19:17
 */
@Service
public class BaseServiceImpl<T> implements IBaseService<T> {

    // 注入BaseMapper
    @Resource
    private BaseMapper<T> baseMapper;
    @Override
    public int save(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int deleteById(Serializable id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int delete(QueryWrapper<T> queryWrapper) {
        return baseMapper.delete(queryWrapper);
    }

    @Override
    public int update(T entity) {
        return baseMapper.update(entity);
    }

    @Override
    public T getById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public T getOne(QueryWrapper<T> queryWrapper) {
        return baseMapper.select(queryWrapper);
    }

    @Override
    public List<T> list(QueryWrapper<T> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

}
