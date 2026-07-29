package org.bluebridge.hdfs.demo_01_helloworld.controller;

import org.apache.hadoop.fs.FileStatus;
import org.bluebridge.hdfs.demo_01_helloworld.service.IHdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * HDFS REST 演示接口
 */
@RestController
@RequestMapping("/hdfs")
public class HdfsController {

    @Autowired
    private IHdfsService hdfsService;

    /**
     * 创建目录
     */
    @PostMapping("/mkdir")
    public String mkdir(@RequestParam String path) throws IOException {
        hdfsService.mkdir(path);
        return "目录创建成功: " + path;
    }

    /**
     * 上传本地文件
     */
    @PostMapping("/upload")
    public String upload(@RequestParam String srcPath, @RequestParam String dstPath) throws IOException {
        hdfsService.uploadFile(srcPath, dstPath);
        return "上传成功: " + dstPath;
    }

    /**
     * 列出目录内容
     */
    @GetMapping("/list")
    public List<FileStatus> list(@RequestParam String path) throws IOException {
        return hdfsService.listFiles(path);
    }

    /**
     * 判断文件是否存在
     */
    @GetMapping("/exists")
    public boolean exists(@RequestParam String path) throws IOException {
        return hdfsService.exists(path);
    }

    /**
     * 删除文件或目录
     */
    @PostMapping("/delete")
    public String delete(@RequestParam String path, @RequestParam(defaultValue = "false") boolean recursive) throws IOException {
        boolean result = hdfsService.delete(path, recursive);
        return result ? "删除成功: " + path : "删除失败: " + path;
    }

    /**
     * 重命名
     */
    @PostMapping("/rename")
    public String rename(@RequestParam String srcPath, @RequestParam String dstPath) throws IOException {
        hdfsService.rename(srcPath, dstPath);
        return "重命名成功";
    }

    /**
     * 下载文件
     */
    @GetMapping("/download")
    public void download(@RequestParam String path, HttpServletResponse response) throws IOException {
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        OutputStream outputStream = response.getOutputStream();
        hdfsService.downloadFile(path, outputStream);
    }
}