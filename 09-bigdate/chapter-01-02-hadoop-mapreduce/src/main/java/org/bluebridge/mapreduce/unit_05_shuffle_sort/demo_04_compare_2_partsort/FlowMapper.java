package org.bluebridge.mapreduce.unit_05_shuffle_sort.demo_04_compare_2_partsort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * shuttle 时使用新创建类继承 WritableComparator 方式对文件进行分区内排序的 FlowMapper 类
 *
 * Mapper：解析每行数据，封装为 FlowWritable 作为 key 输出，交由 Shuffle 按总流量排序
 *
 * @author lingwh
 * @date 2026/8/1 21:52
 */
public class FlowMapper extends Mapper<LongWritable, Text, FlowWritable, NullWritable> {

    private FlowWritable outKey = new FlowWritable();

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
