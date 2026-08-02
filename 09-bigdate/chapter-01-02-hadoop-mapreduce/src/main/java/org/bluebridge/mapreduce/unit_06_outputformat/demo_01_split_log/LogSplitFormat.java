package org.bluebridge.mapreduce.unit_06_outputformat.demo_01_split_log;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 按需求分割日志到不同文件的 LogSplitFormat 类
 *
 * @author lingwh
 * @date 2026/8/2 19:33
 */
public class LogSplitFormat extends FileOutputFormat<Text, NullWritable> {

    @Override
    public RecordWriter<Text, NullWritable> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new LogWriter(context);
    }
}
