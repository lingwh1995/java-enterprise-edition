package org.bluebridge.hdfs;

import org.apache.hadoop.fs.FileStatus;
import org.bluebridge.hdfs.demo_01_helloworld.service.IHdfsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * HDFS API 测试客户端
 *
 * @author lingwh
 * @date 2025/7/10 15:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Client {

    @Autowired
    private IHdfsService hdfsService;

    /**
     * 测试创建目录
     */
    @Test
    public void testMkdir() throws IOException {
        hdfsService.mkdir("/input");
    }

    /**
     * 测试判断文件是否存在
     */
    @Test
    public void testExists() throws IOException {
        System.out.println(hdfsService.exists("/input"));
    }

    /**
     * 测试列出目录
     */
    @Test
    public void testListFiles() throws IOException {
        System.out.println(hdfsService.listFiles("/"));
    }

    /**
     * 测试上传本地文件
     */
    @Test
    public void testUploadFile() throws IOException {
        String localPath = this.getClass().getClassLoader().getResource("input.txt").getPath();
        hdfsService.uploadFile(localPath, "/input/input.txt");
    }

    /**
     * 测试通过输入流上传文件
     */
    @Test
    public void testUploadFileByInputStream() throws IOException {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("input.txt")) {
            hdfsService.uploadFile(inputStream, "/input/input.txt");
        }
    }

    /**
     * 测试从本地复制文件到 HDFS
     */
    @Test
    public void testCopyFromLocal() throws IOException {
        String localPath = this.getClass().getClassLoader().getResource("input.txt").getPath();
        hdfsService.copyFromLocal(localPath, "/input/input_copyfromlocal.txt");
    }

    /**
     * 测试下载文件到本地
     */
    @Test
    public void testDownloadFile() throws IOException {
        String localPath = System.getProperty("java.io.tmpdir") + "downloaded.txt";
        hdfsService.downloadFile("/input/input.txt", localPath);
        System.out.println("下载到: " + localPath);
    }

    /**
     * 测试通过输出流下载文件
     */
    @Test
    public void testDownloadFileByOutputStream() throws IOException {
        String localPath = System.getProperty("java.io.tmpdir") + "downloaded.txt";
        try (OutputStream outputStream = new FileOutputStream(localPath)) {
            hdfsService.downloadFile("/input/input.txt", outputStream);
        }
        System.out.println("下载到: " + localPath);
    }

    /**
     * 测试删除文件或目录
     */
    @Test
    public void testDelete() throws IOException {
        boolean result = hdfsService.delete("/input", true);
        System.out.println(result ? "删除成功" : "删除失败");
    }

    /**
     * 测试重命名
     */
    @Test
    public void testRename() throws IOException {
        hdfsService.rename("/input/input.txt", "/input/input_renamed.txt");
    }

    /**
     * 测试 HDFS 内部复制
     */
    @Test
    public void testCopy() throws IOException {
        hdfsService.copy("/input/input.txt", "/input/input_copy.txt");
    }

    /**
     * 测试获取文件状态
     */
    @Test
    public void testGetFileStatus() throws IOException {
        FileStatus fileStatus = hdfsService.getFileStatus("/input/input.txt");
        System.out.println(fileStatus);
    }

    /**
     * 测试写入文件
     */
    @Test
    public void testWriteFile() throws IOException {
        hdfsService.writeFile("/input/hello.txt", "Hello HDFS!");
    }

    /**
     * 测试读取文件
     */
    @Test
    public void testReadFile() throws IOException {
        String content = hdfsService.readFile("/input/hello.txt");
        System.out.println(content);
    }
}