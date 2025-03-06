package org.bluebridge.extend.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 以下定义的 4个 default method, copy from {@link com.baomidou.mybatisplus.extension.toolkit.ChainWrappers}
     */
    default QueryChainWrapper<T> queryChain() {
        return new QueryChainWrapper<>(this);
    }

    default LambdaQueryChainWrapper<T> lambdaQueryChain() {
        return new LambdaQueryChainWrapper<>(this);
    }

    default UpdateChainWrapper<T> updateChain() {
        return new UpdateChainWrapper<>(this);
    }

    default LambdaUpdateChainWrapper<T> lambdaUpdateChain() {
        return new LambdaUpdateChainWrapper<>(this);
    }

    /**
     * 以下定义的 4个 method 其中 3 个是内置的选装件
     */
    /**
     * 功能：
     *      这个方法用于批量插入实体对象，但只插入实体对象中指定的某些字段。这在需要批量插入数据，但又不希望插入所
     *      有字段时非常有用。
     * 使用场景：
     *      当你需要批量插入数据，并且希望只插入实体对象中的部分字段，以提高插入效率或保护敏感数据。
     * @param entityList
     * @return
     */
    int insertBatchSomeColumn(List<T> entityList);

    /**
     * 功能：
     *      这个方法用于在更新操作时，无论实体对象的某些字段是否有变化，都会强制更新这些字段。这在某些业务场景下非常
     *      有用，比如更新时间戳字段，确保每次更新操作都会更新该字段。
     * 使用场景：
     *      当你需要在每次更新记录时，都更新某些特定的字段（如更新时间、版本号等），即使这些字段在实体对象中没有变化。
     * @param entity
     * @return
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);

    /**
     * 功能：
     *      这个方法用于逻辑删除记录，并填充实体对象中的某些字段。逻辑删除意味着不是真正从数据库中删除记录，而
     *      是通过更新某个字段（如 deleted 字段）来标记记录已被删除。
     * 使用场景：
     *      当你需要实现逻辑删除功能，并且希望在删除操作时自动填充实体对象中的某些字段（如删除时间、删除人等）。
     * @param entity
     * @return
     */
//    int logicDeleteByIdWithFill(T entity);

    int deleteByIdWithFill(T entity);

    /**
     * 以下为自己自定义
     */
    T findOne(Serializable id);

    int deleteAll();
}
