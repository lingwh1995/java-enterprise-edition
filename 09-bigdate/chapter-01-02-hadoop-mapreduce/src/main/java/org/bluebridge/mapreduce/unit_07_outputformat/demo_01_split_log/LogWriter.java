package org.bluebridge.mapreduce.unit_07_outputformat.demo_01_split_log;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 *
 * @author lingwh
 * @date 2026/8/2 19:48
 */
public class LogWriter extends RecordWriter<Text, NullWritable> {

    /**
     * Hadoop 日志文件路径 - 会生成到当前项目根目录下的 hadoop 目录下
     */
    private String hadoopLogPath = "hadoop/output/unit_06_outputformat/demo_01_split_log/hadoop.log";

    /**
     * other 日志文件路径 - 会生成到当前项目根目录下的 hadoop 目录下
     */
    private String otherLogPath = "hadoop/output/unit_06_outputformat/demo_01_split_log/other.log";

    FileSystem fileSystem;
    FSDataOutputStream hadoopLogOutputStream;
    FSDataOutputStream otherLogOutputStream;

    public LogWriter(TaskAttemptContext context) throws IOException {
        Configuration configuration = context.getConfiguration();
        // 获取文件系统对象
        fileSystem = FileSystem.getLocal(configuration);
        // 获取流输出对象
        hadoopLogOutputStream = fileSystem.create(new Path(hadoopLogPath));
        otherLogOutputStream = fileSystem.create(new Path(otherLogPath));
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        // 获取当前写出数据
        String lineData = key.toString();
        if (lineData.contains("hadoop")) {
            hadoopLogOutputStream.write((lineData + "\n").getBytes());
        } else {
            otherLogOutputStream.write((lineData + "\n").getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStream(fileSystem);
        IOUtils.closeStream(hadoopLogOutputStream);
        IOUtils.closeStream(otherLogOutputStream);
    }
}
