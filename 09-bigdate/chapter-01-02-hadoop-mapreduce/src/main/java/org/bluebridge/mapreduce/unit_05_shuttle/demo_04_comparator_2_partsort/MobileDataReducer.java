package org.bluebridge.mapreduce.unit_05_shuttle.demo_04_comparator_2_partsort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * shuttle 时使用继承类 WritableComparator 方式对文件进行分区内排序的 MobileDataReducer 类
 *
 * Reducer：接收已排序的 <MobileData, NullWritable>，输出 <手机号, MobileData>
 *
 * @author lingwh
 * @date 2026/8/2 22:07
 */
public class MobileDataReducer extends Reducer<MobileDataWritable, NullWritable, Text, MobileDataWritable> {

    private Text outKey = new Text();

    @Override
    protected void reduce(MobileDataWritable key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        outKey.set(key.getPhoneNumber());
        context.write(outKey, key);
    }
}
