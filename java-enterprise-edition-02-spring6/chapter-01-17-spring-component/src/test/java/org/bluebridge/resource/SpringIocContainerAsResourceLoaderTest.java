package org.bluebridge.resource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 测试Spring的IoC容器将自身的引用赋值给ResourceLoaderAware的实现类中的ResourceLoader类型的属性
 */
public class SpringIocContainerAsResourceLoaderTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringIocContainerAsResourceLoaderTest.class);

    @Test
    public void testSpringIocContainerAsResourceLoader() {
        //Spring的IoC容器的实例
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-resourceaware.xml");
        //从Spring的IoC容器中共获取ResourceLoaderAware的实现类
        SpringIocContainerAsResourceLoader springIocContainerAsResourceLoader = applicationContext.getBean("springIocContainerAsResourceLoader", SpringIocContainerAsResourceLoader.class);
        //获取ResourceLoaderAware的实现类中的ResourceLoader类型的属性的值
        ResourceLoader resourceLoader = springIocContainerAsResourceLoader.getResourceLoader();
        logger.info(String.valueOf(applicationContext == resourceLoader));

        //使用ResourceLoaderAware的实现类中的ResourceLoader加载文件
        //当传入的路径带有 classpath 时，ReosurceLoader会自动选择ClassPathResource作为实现类
        Resource classPathResource = resourceLoader.getResource("classpath:applicationContext-resource.xml");
        logger.info(classPathResource.getClass().getName());
        //当传入的路径带有 file 时，ReosurceLoader会自动选择FileUrlResource作为实现类
        Resource fileUrlResource = resourceLoader.getResource("file:applicationContext-resource.xml");
        logger.info(fileUrlResource.getClass().getName());
        //当传入的路径带有 http 时，ReosurceLoader会自动选择UrlResource作为实现类
        Resource urlResource = resourceLoader.getResource("http://www.baidu.com");
        logger.info(urlResource.getClass().getName());
    }
}
