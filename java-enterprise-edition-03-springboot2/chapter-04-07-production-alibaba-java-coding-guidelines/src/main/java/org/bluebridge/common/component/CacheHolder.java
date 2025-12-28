package org.bluebridge.common.component;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lingwh
 * @desc 缓存管理器
 * @date 2025/12/10 14:05
 */
@Component
public class CacheHolder<T> {

    /**
     * 使用 final 确保引用不可变，防御多线程下的引用篡改
     */
    private final Map<String, T> pool = new ConcurrentHashMap<>();

    /**
     * 获取缓存对象: 使用 Optional 包装，避免调用方直接处理 null
     * @param key 缓存键
     * @return 缓存的对象
     */
    public Optional<T> get(String key) {
        if (key == null) return Optional.empty();
        return Optional.ofNullable(pool.get(key));
    }

    /**
     * 缓存对象
     * @param key 缓存键
     * @param value 缓存值
     */
    public void put(String key, T value) {
        if (key != null && value != null) {
            pool.put(key, value);
        }
    }

    /**
     * 批量操作扩展
     */
    public void putAll(Map<? extends String, ? extends T> m) {
        if (m != null) {
            pool.putAll(m);
        }
    }

    /**
     * 移除缓存对象
     * @param key 缓存键
     */
    public void remove(String key) {
        if (key != null) {
            pool.remove(key);
        }
    }

    /**
     * 获取缓存大小
     * @return 缓存大小
     */
    public int size() {
        return pool.size();
    }

    /**
     * 清空缓存
     */
    public void clear() {
        pool.clear();
    }

}