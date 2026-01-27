package org.bluebridge.resource.file_system_resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * 访问文件系统的中资源
 */
public class FileSystemResourceDemo {
    private static final Logger logger = LogManager.getLogger(FileSystemResourceDemo.class);

    /**
     * FileSystemResource 读取文件系统中的资源
     * @param path
     * @throws IOException
     */
    public void loadAndParseFileSystemResource(String path) throws IOException {
        //1.创建Resource实现类FileSystemResource
        Resource resource = new FileSystemResource(path);
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
