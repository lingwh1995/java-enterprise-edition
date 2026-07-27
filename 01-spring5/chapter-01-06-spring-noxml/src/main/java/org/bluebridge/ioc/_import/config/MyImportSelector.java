package org.bluebridge.ioc._import.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 在 ImportSelector 中自定义逻辑，返回需要导入的组件
 *
 * @author lingwh
 * @date 2019/4/13 14:29
 */
public class MyImportSelector implements ImportSelector {

    /**
     * @param importingClassMetadata 当前标注 @Import 注解的类的所有注解信息
     * @return 导入到容器中的组件全类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"org.bluebridge.ioc._import.domain.Blue","org.bluebridge.ioc._import.domain.Yellow"};
    }
}
