package org.bluebridge.ioc.customercomponment.domain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author lingwh
 * @desc   Spring框架会回调这些实现了XXXAware的方法
 * @date   2019/4/6 13:43
 */
@Component
public class Red implements ApplicationContextAware,BeanNameAware,
        EmbeddedValueResolverAware{
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("传入的iooc:"+applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("当前bean的名字:"+name);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String resolveStringValue = resolver.resolveStringValue("你好${os.name}我是#{44*2}");
        System.out.println("解析的字符串:"+resolveStringValue);
    }
}
