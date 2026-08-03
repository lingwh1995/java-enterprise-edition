package org.bluebridge.mapreduce.unit_07_outputformat.demo_01_split_log;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * LogSplitFormat 类的 LogSplitMapper 类
 *
 * @author lingwh
 * @date 2026/8/2 18:48
 */
public class LogSplitMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        context.write(value, NullWritable.get());
    }
}