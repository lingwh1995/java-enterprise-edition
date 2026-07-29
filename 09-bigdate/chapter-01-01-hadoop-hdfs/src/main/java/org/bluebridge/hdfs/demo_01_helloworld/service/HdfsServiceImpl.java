package org.bluebridge.hdfs.demo_01_helloworld.service;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class HdfsServiceImpl implements IHdfsService {

    @Autowired
    private FileSystem fileSystem;

    @Override
    public void mkdir(String path) throws IOException {
        fileSystem.mkdirs(new Path(path));
    }

    @Override
    public void uploadFile(String srcPath, String dstPath) throws IOException {
        fileSystem.copyFromLocalFile(new Path(srcPath), new Path(dstPath));
    }

    @Override
    public void uploadFile(InputStream inputStream, String dstPath) throws IOException {
        try (FSDataOutputStream outputStream = fileSystem.create(new Path(dstPath), true)) {
            IOUtils.copyBytes(inputStream, outputStream, 4096, false);
        }
    }

    @Override
    public void copyFromLocal(String srcPath, String dstPath) throws IOException {
        fileSystem.copyFromLocalFile(new Path(srcPath), new Path(dstPath));
    }

    @Override
    public void downloadFile(String srcPath, String localDstPath) throws IOException {
        fileSystem.copyToLocalFile(new Path(srcPath), new Path(localDstPath));
    }

    @Override
    public void downloadFile(String srcPath, OutputStream outputStream) throws IOException {
        try (FSDataInputStream inputStream = fileSystem.open(new Path(srcPath))) {
            IOUtils.copyBytes(inputStream, outputStream, 4096, false);
        }
    }

    @Override
    public boolean delete(String path, boolean recursive) throws IOException {
        return fileSystem.delete(new Path(path), recursive);
    }

    @Override
    public boolean exists(String path) throws IOException {
        return fileSystem.exists(new Path(path));
    }

    @Override
    public void rename(String srcPath, String dstPath) throws IOException {
        fileSystem.rename(new Path(srcPath), new Path(dstPath));
    }

    @Override
    public void copy(String srcPath, String dstPath) throws IOException {
        FileUtil.copy(fileSystem, new Path(srcPath), fileSystem, new Path(dstPath), false, fileSystem.getConf());
    }

    @Override
    public List<FileStatus> listFiles(String path) throws IOException {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path(path));
        List<FileStatus> result = new ArrayList<>();
        if (fileStatuses != null) {
            for (FileStatus fileStatus : fileStatuses) {
                result.add(fileStatus);
            }
        }
        return result;
    }

    @Override
    public FileStatus getFileStatus(String path) throws IOException {
        return fileSystem.getFileStatus(new Path(path));
    }

    @Override
    public String readFile(String path) throws IOException {
        try (FSDataInputStream inputStream = fileSystem.open(new Path(path));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copyBytes(inputStream, outputStream, 4096, true);
            return outputStream.toString(StandardCharsets.UTF_8.name());
        }
    }

    @Override
    public void writeFile(String path, String content) throws IOException {
        try (FSDataOutputStream outputStream = fileSystem.create(new Path(path), true)) {
            outputStream.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }
}