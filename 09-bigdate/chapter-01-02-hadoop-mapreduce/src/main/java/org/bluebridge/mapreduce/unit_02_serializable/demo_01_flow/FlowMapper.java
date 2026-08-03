package org.bluebridge.mapreduce.unit_02_serializable.demo_01_flow;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 自定义序列化对象实现合并相同手机号数据的 Mapper 类
 *
 * 针对当前一行数据进行切割，切割后搜集手机号和流量信息，最后封装输出结果
 *
 * @author lingwh
 * @date 2026/7/19 19:35
 */
public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowWritable> {

    private Text outKey = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取当前行数据
        String line = value.toString();
        // 切割数据（更强的正则表达式，匹配多个空格或制表符等，可以防止空字符串进入数组）
        String[] words = line.split("\\s+");

        // 使用手机号作为键
        outKey.set(words[1]);
        // 使用 Flow 类封装流量信息
        FlowWritable flowWritable = new FlowWritable();
        flowWritable.setUplinkData(Integer.parseInt(words[words.length - 3]));
        flowWritable.setDownlinkData(Integer.parseInt(words[words.length - 2]));
        flowWritable.setSumData();
        context.write(outKey, flowWritable);
    }
}