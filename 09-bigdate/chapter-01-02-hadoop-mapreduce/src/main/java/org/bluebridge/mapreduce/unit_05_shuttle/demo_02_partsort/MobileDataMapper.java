package org.bluebridge.mapreduce.unit_05_shuttle.demo_02_partsort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 对文件进行分区排序的 MobileDataMapper 类
 *
 * Mapper：解析每行数据，封装为 MobileData 作为 key 输出，交由 Shuffle 按总流量排序
 *
 * @author lingwh
 * @date 2026/7/19 19:35
 */
public class MobileDataMapper extends Mapper<LongWritable, Text, MobileData, NullWritable> {

    private MobileData outKey = new MobileData();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split("\\s+");

        outKey.setPhoneNumber(words[1]);
        outKey.setUplinkData(Integer.parseInt(words[words.length - 3]));
        outKey.setDownlinkData(Integer.parseInt(words[words.length - 2]));
        outKey.setSumData();
        context.write(outKey, NullWritable.get());
    }
}
