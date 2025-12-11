package org.bluebridge.wrapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/11 18:37
 */
public interface Wrapper<T> extends Serializable {

    String getSqlSegment();
    List<Object> getParams();
    Class<T> getEntityClass();

}
