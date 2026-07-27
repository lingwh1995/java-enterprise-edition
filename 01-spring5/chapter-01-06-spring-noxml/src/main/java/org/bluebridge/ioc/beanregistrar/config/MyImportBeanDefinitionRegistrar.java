package org.bluebridge.ioc.beanregistrar.config;

import org.bluebridge.ioc.beanregistrar.domain.Rainbow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义 ImportBeanDefinitionRegistrar
 *
 * @author lingwh
 * @date 2019/4/9 14:29
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * @param importingClassMetadata 当前类的注解信息
     * @param registry 把所有添加到容器中的 bean，调用：registry.registerBeanDefinition()手工注册进来
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        // 判断容器中是否有红色和蓝色
        boolean hasRed = registry.containsBeanDefinition("org.bluebridge.ioc.beanregistrar.domain.Red");
        boolean hasBlue = registry.containsBeanDefinition("org.bluebridge.ioc.beanregistrar.domain.Rlue");
        if(hasRed || hasBlue){
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Rainbow.class);
            registry.registerBeanDefinition("rainBow",beanDefinition);
        }
    }
}
