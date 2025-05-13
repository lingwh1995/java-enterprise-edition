package org.bluebridge.resource.uriresource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * 访问网络资源演示
 */
public class UrlResourceDemo {
    private static final Logger logger = LogManager.getLogger(UrlResourceDemo.class);

    /**
     * UrlResource 读取http/file/ftp前缀的资源路径
     * @param path
     * @throws IOException
     */
    public void loadAndParseUrlResource(String path) throws IOException {
        //1.创建Resource实现类UrlResource
        Resource resource = new UrlResource(path);
        //2.获取资源信息
            //获取资源名称
        String filename = resource.getFilename();
        logger.info("资源名称: " + filename);
            //获取资源uri
        URI uri = resource.getURI();
        logger.info("资源uri: " + uri);
            //获取资源url
        URL url = resource.getURL();
        logger.info("资源url: " + url);
            //获取资源description
        String description = resource.getDescription();
        logger.info("资源description: " + description);
            //读取资源内容
        logger.info("资源内容: ");
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) != -1) {
                logger.info(new String(buffer));
            }
        }catch (IOException e) {

        }finally {
            inputStream.close();
        }
    }
}
