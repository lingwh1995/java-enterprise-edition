package org.bluebridge.hdfs.demo_01_helloworld.service;

import org.apache.hadoop.fs.FileStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface IHdfsService {

    void mkdir(String path) throws IOException;

    void uploadFile(String srcPath, String dstPath) throws IOException;

    void uploadFile(InputStream inputStream, String dstPath) throws IOException;

    void copyFromLocal(String srcPath, String dstPath) throws IOException;

    void downloadFile(String srcPath, String localDstPath) throws IOException;

    void downloadFile(String srcPath, OutputStream outputStream) throws IOException;

    boolean delete(String path, boolean recursive) throws IOException;

    boolean exists(String path) throws IOException;

    void rename(String srcPath, String dstPath) throws IOException;

    void copy(String srcPath, String dstPath) throws IOException;

    List<FileStatus> listFiles(String path) throws IOException;

    FileStatus getFileStatus(String path) throws IOException;

    String readFile(String path) throws IOException;

    void writeFile(String path, String content) throws IOException;
}