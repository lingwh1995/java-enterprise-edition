package org.bluebridge.mapreduce.unit_05_shuttle.demo_03_comparator_2_fullsort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * shuttle 时使用新创建类继承 WritableComparator 方式对文件进行全排序的 MobileDataReducer 类
 *
 * Reducer：接收已排序的 <MobileData, NullWritable>，输出 <手机号, MobileData>
 *
 * @author lingwh
 * @date 2026/8/1 20:32
 */
public class MobileDataReducer extends Reducer<MobileDataWritable, NullWritable, Text, MobileDataWritable> {

    private Text outKey = new Text();

    @Override
    protected void reduce(MobileDataWritable key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        outKey.set(key.getPhoneNumber());
        context.write(outKey, key);
    }
}
