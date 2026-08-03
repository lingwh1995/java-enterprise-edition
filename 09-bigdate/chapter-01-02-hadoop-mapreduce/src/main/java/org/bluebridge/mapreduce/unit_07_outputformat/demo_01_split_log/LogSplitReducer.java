package org.bluebridge.mapreduce.unit_07_outputformat.demo_01_split_log;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * LogSplitFormat 类的 LogSplitReducer 类
 *
 * @author lingwh
 * @date 2026/8/2 18:49
 */
public class LogSplitReducer extends Reducer<Text, NullWritable, Text, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Reducer<Text, NullWritable, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        for (NullWritable value : values) {
            context.write(key, value);
        }
    }
}
