package org.bluebridge.resource.auto_choice_resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 *  Resource在Spring容器中的最佳使用方式   在IoC容器中根据配置的Resouce类型的属性的value的值的前缀自动注入一个Resource接口的实现类
 *      1.与Spring的IoC容器紧密结合，采用配置文件方式避免了硬编码带来的代码耦合问题
 *      2.Spring容器会作为ResourceLoader会自动为我们选择合适的Resource接口实现类，不用我们自己决定使用什么实现类了
 *          这里要注意: Spring将自己的IoC容器自身作为ResourceLoader使用了
 */
public class AutoChoiceResourceBySpringIoC {

    private static final Logger logger = LogManager.getLogger(AutoChoiceResourceBySpringIoC.class);

    private Resource resource;

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    /**
     * 解析Resource中包含的内容
     * @throws IOException
     */
    public void parseResource() throws IOException {
        //1.创建Resource实现类ClassPathResource
            //从Spring容器中获取该实例对象
        //2.获取资源信息
        //获取资源名称
        String filename = this.resource.getFilename();
        logger.info("资源名称: " + filename);
        //获取资源uri
        URI uri = this.resource.getURI();
        logger.info("资源uri: " + uri);
        //获取资源url
        URL url = this.resource.getURL();
        logger.info("资源url: " + url);
        //获取资源description
        String description = this.resource.getDescription();
        logger.info("资源description: " + description);
        //读取资源内容
        logger.info("资源内容: ");
        InputStream inputStream = inputStream = this.resource.getInputStream();
        byte[] buffer = new byte[1024];
        while (inputStream.read(buffer) != -1) {
            logger.info(new String(buffer));
        }
    }

}
