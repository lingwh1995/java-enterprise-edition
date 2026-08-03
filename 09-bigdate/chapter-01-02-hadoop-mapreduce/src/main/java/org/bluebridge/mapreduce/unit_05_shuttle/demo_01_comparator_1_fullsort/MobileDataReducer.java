package org.bluebridge.mapreduce.unit_05_shuttle.demo_01_comparator_1_fullsort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * shuttle时使用实体类实现接口 WritableComparable 方式对文件进行全排序的 MobileDataReducer 类
 *
 * Reducer：接收已排序的 <MobileData, NullWritable>，输出 <手机号, MobileData>
 *
 * @author lingwh
 * @date 2026/8/1 18:38
 */
public class MobileDataReducer extends Reducer<MobileDataWritable, NullWritable, Text, MobileDataWritable> {

    private Text outKey = new Text();

    @Override
    protected void reduce(MobileDataWritable key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        outKey.set(key.getPhoneNumber());
        context.write(outKey, key);
    }
}
