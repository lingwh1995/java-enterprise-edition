package org.bluebridge.ioc._import.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author ronin
 */
//自定义逻辑,返回需要导入的组件
public class MyImportSelector implements ImportSelector {

    /**
     * @param importingClassMetadata 当前标注@Import注解的类的所有注解信息
     * @return 导入到容器中的组件全类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"org.bluebridge.ioc._import.domain.Blue","org.bluebridge.ioc._import.domain.Yellow"};
    }
}
