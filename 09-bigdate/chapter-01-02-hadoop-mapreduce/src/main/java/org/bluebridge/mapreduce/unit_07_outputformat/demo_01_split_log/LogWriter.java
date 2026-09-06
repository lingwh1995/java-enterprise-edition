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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 *
 * @author lingwh
 * @date 2026/8/2 19:48
 */
public class LogWriter extends RecordWriter<Text, NullWritable> {

    FileSystem fileSystem;

    FSDataOutputStream hadoopLogOutputStream;

    FSDataOutputStream otherLogOutputStream;

    public LogWriter(TaskAttemptContext context) throws IOException {
        Configuration configuration = context.getConfiguration();
        // 获取 Driver 中设置的输出路径，日志文件输出到该路径下
        Path outputPath = FileOutputFormat.getOutputPath(context);
        // 获取输出路径对应的文件系统对象（本地运行为本地文件系统，集群运行为 HDFS）
        fileSystem = outputPath.getFileSystem(configuration);
        // 拼接两个日志文件的完整路径
        Path hadoopLogPath = new Path(outputPath, "hadoop.log");
        Path otherLogPath = new Path(outputPath, "other.log");
        // 获取流输出对象
        hadoopLogOutputStream = fileSystem.create(hadoopLogPath);
        otherLogOutputStream = fileSystem.create(otherLogPath);
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
        // 只关闭输出流，FileSystem 是全局缓存的共享对象，不应在此关闭
        IOUtils.closeStream(hadoopLogOutputStream);
        IOUtils.closeStream(otherLogOutputStream);
    }
}
