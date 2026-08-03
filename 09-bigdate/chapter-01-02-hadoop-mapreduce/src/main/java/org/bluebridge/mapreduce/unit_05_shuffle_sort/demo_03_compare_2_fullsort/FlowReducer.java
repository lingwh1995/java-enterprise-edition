package org.bluebridge.mapreduce.unit_05_shuffle_sort.demo_03_compare_2_fullsort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * shuttle 时使用新创建类继承 WritableComparator 方式对文件进行全排序的 FlowReducer 类
 *
 * Reducer：接收已排序的 <FlowWritable, NullWritable>，输出 <手机号, FlowWritable>
 *
 * @author lingwh
 * @date 2026/8/1 20:32
 */
public class FlowReducer extends Reducer<FlowWritable, NullWritable, Text, FlowWritable> {

    private Text outKey = new Text();

    @Override
    protected void reduce(FlowWritable key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        outKey.set(key.getPhoneNumber());
        context.write(outKey, key);
    }
}
