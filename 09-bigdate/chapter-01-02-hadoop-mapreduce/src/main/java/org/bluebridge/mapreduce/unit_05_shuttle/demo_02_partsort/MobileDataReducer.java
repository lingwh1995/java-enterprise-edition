package org.bluebridge.mapreduce.unit_05_shuttle.demo_02_partsort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 对文件进行分区排序的 MobileDataReducer 类
 *
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
