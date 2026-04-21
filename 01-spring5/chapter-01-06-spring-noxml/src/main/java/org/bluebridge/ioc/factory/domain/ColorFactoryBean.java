package org.bluebridge.ioc.factory.domain;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

/**
 * @author lingwh
 * @desc   Spring循环依赖
 * @date   2019/4/5 14:42
 */
public class ColorFactoryBean implements FactoryBean<Color>{

    @Nullable
    @Override
    public Color getObject() throws Exception {
        System.out.println("Color被创建了......");
        return new Color();
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    /**
     * 是否为单例bean，如果这个bean是单实例,在容器中保存一份
     * false:多实例,m每次获取都会创建一个新的bean
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
