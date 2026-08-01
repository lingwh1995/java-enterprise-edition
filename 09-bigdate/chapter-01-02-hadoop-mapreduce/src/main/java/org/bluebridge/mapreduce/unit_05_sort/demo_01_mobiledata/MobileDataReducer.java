package org.bluebridge.mapreduce.unit_05_sort.demo_01_mobiledata;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Reducer：接收已排序的 <MobileData, NullWritable>，输出 <手机号, MobileData>
 *
 * @author lingwh
 * @date 2026/7/19 18:06
 */
public class MobileDataReducer extends Reducer<MobileData, NullWritable, Text, MobileData> {

    private Text outKey = new Text();

    @Override
    protected void reduce(MobileData key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        outKey.set(key.getPhoneNumber());
        context.write(outKey, key);
    }
}
