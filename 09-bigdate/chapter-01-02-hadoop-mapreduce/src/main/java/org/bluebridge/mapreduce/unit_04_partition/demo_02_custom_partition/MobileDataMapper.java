package org.bluebridge.mapreduce.unit_04_partition.demo_02_custom_partition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Mapper 类，针对当前一行数据进行切割，切割后搜集手机号和流量信息，最后封装输出结果
 *
 * @author lingwh
 * @date 2025/8/20 16:44
 */
public class MobileDataMapper extends Mapper<LongWritable, Text, Text, MobileData> {

    private Text outKey = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取当前行数据
        String line = value.toString();
        // 切割数据（更强的正则表达式，匹配多个空格或制表符等，可以防止空字符串进入数组）
        String[] words = line.split("\\s+");

        // 使用手机号作为键
        outKey.set(words[1]);
        // 使用 MobileData 类封装流量信息
        MobileData mobileData = new MobileData();
        mobileData.setUplinkData(Integer.parseInt(words[words.length - 3]));
        mobileData.setDownlinkData(Integer.parseInt(words[words.length - 2]));
        mobileData.setSumData();
        context.write(outKey, mobileData);
    }
}