package org.bluebridge;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

//SpingMVC6.x上传功能需要在放在所在的类上添加@MultipartConfig注解
@MultipartConfig
@RequestMapping(value = "/fileUploadAndDownload")
@Controller
public class FileUploadAndDownloadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadAndDownloadController.class);

    /**
     * 文件下载
     * @param session
     * @param fileName
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(HttpSession session, @PathVariable("fileName") String fileName) throws IOException {
        //1.获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //2.获取服务器中文件的真实路径
        String realPath = servletContext.getRealPath(new StringBuilder("/static/download/").append(fileName).toString());
        logger.info(realPath);
        //3.使用commons-io工具类把文件转为字节数组
        byte[] byteArray = FileUtils.readFileToByteArray(new File(realPath));
        //4.设置要下载方式以及下载文件的名字
        String downloadFileName = new String(fileName.getBytes("utf-8"),"utf-8");//设置编码
        //5.创建HttpHeaders对象设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", downloadFileName);
            //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //6.设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //7.创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(byteArray, headers, statusCode);
        return responseEntity;
    }

    /**
     * 单文件上传(SpingMVC6.x上传功能需要在放在所在的类上添加@MultipartConfig注解)
     * @param uploadFile
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload/file")
    public String uploadFile(@RequestParam("uploadFile") MultipartFile uploadFile, HttpSession session) throws IOException {
        //1.获取上传的文件的文件名,主要是用来确定文件的后缀
        String fileName = uploadFile.getOriginalFilename();
        //2.处理文件重名问题,从最后一个"."开始截取直到最后就是上传文件的后缀名,
        String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));
        logger.info("文件名后缀: " + fileNameSuffix);
        //3.使用java.util包下的UUID生成随机值的工具类
        fileName = (UUID.randomUUID().toString()).replaceAll("-","") + fileNameSuffix;
        logger.info(fileName);
        //4.设置文件上传的位置比如服务器中的upload目录,这个目录如果不存在我们还需要创建一个
        ServletContext servletContext = session.getServletContext();
        String uploadDirRealPath = servletContext.getRealPath("upload");
        File uploadDir = new File(uploadDirRealPath);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        //5.获取文件最终在服务器中的位置
        String fileDestRealPath = uploadDir + File.separator + fileName;
        //6.实现上传功能(本质是文件的复制,先读后写),将MultipartFile中封装的文件数据转移到最终的文件位置
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(),new File(fileDestRealPath));
        return "success";
    }

    /**
     * 多文件上传(SpingMVC6.x上传功能需要在放在所在的类上添加@MultipartConfig注解)
     * @param uploadFiles
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload/files")
    public String uploadFiles(@RequestParam("uploadFiles") MultipartFile[] uploadFiles, HttpSession session) throws IOException {
        for(MultipartFile uploadFile: uploadFiles) {
            //1.获取上传的文件的文件名,主要是用来确定文件的后缀
            String fileName = uploadFile.getOriginalFilename();
            //2.处理文件重名问题,从最后一个"."开始截取直到最后就是上传文件的后缀名,
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));
            logger.info("文件名后缀: " + fileNameSuffix);
            //3.使用java.util包下的UUID生成随机值的工具类
            fileName = (UUID.randomUUID().toString()).replaceAll("-","") + fileNameSuffix;
            logger.info(fileName);
            //4.设置文件上传的位置比如服务器中的upload目录,这个目录如果不存在我们还需要创建一个
            ServletContext servletContext = session.getServletContext();
            String uploadDirRealPath = servletContext.getRealPath("upload");
            File uploadDir = new File(uploadDirRealPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            //5.获取文件最终在服务器中的位置
            String fileDestRealPath = uploadDir + File.separator + fileName;
            //6.实现上传功能(本质是文件的复制,先读后写),将MultipartFile中封装的文件数据转移到最终的文件位置
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(),new File(fileDestRealPath));
        }
        return "success";
    }

}
