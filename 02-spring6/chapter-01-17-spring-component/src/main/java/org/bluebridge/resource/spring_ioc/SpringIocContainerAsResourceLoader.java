package org.bluebridge.resource.spring_ioc;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * 验证Spring的IoC容器自身就可以充当ResourceLoader
 *      ResourceLoaderAware接口实现类的实例将获得一个ResourceLoader的引用，ResourceLoaderAware接口也提供了一个setResourceLoader()方法，
 *      该方法将由Spring容器负责调用，Spring容器会将一个ResourceLoader对象作为该方法的参数传入。如果把实现ResourceLoaderAware接口的Bean类部署在Spring容器中，Spring容器
 *      会将自身当成ResourceLoader作为setResourceLoader()方法的参数传入。由于ApplicationContext的实现类都实现了ResourceLoader接口，Spring容器自身完全可作为ResorceLoader使用。
 */
public class SpringIocContainerAsResourceLoader implements ResourceLoaderAware {
    //注意:在配置文件中配置bean时，我们不会给这个属性赋值，这个属性的赋值由Spring容器自己完成
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 这个方法用于外部获取ResourceLoader的实例对象的引用
     * @return
     */
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}
