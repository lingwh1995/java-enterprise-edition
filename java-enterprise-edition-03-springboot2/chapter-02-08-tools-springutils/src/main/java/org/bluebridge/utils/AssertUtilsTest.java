package org.bluebridge.utils;

import org.junit.Test;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 断言工具类
 */
public class AssertUtilsTest {

    /**
     * 断言参数是否为空，如果不满足条件，则直接抛异常。
     * @throws Exception
     */
    @Test
    public void testAssertParameterIsNotNull() throws Exception{
        String str = null;
        Assert.isNull(str, "str必须为空");
        Assert.isNull(str, () -> "str必须为空");
        Assert.notNull(str, "str不能为空");
    }

    /**
     * 断言集合是否空，如果不满足条件，则直接抛异常。
     * @throws Exception
     */
    @Test
    public void testAssertCollectionIsNotEmpty() throws Exception{
        List<String> list = null;
            Map<String, String> map = null;
            Assert.notEmpty(list, "list不能为空");
            Assert.notEmpty(list, () -> "list不能为空");
            Assert.notEmpty(map, "map不能为空");
    }

    /**
     * 断言条件是否空，如果不满足条件，则直接抛异常。
     * @throws Exception
     */
    @Test
    public void testAssertConditionIsNotEmpty() throws Exception{
        boolean condition = false;
        Assert.isTrue(condition, "条件不能为假");
        Assert.isTrue(condition, () -> "条件不能为假");
    }

}
