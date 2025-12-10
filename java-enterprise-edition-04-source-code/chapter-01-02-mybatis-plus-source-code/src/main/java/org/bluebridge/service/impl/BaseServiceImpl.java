package org.bluebridge.service.impl;

import org.bluebridge.mapper.BaseMapper;
import org.bluebridge.service.IBaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public int insert(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int deleteById(Object id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int delete(T entity) {
        return baseMapper.delete(entity);
    }

    @Override
    public int update(T entity) {
        return baseMapper.update(entity);
    }

    @Override
    public T selectById(Object id) {
        return baseMapper.selectById(id);
    }

    @Override
    public T select(T entity) {
        return baseMapper.select(entity);
    }

    @Override
    public List<T> selectList(T entity) {
        return baseMapper.selectList(entity);
    }

}
