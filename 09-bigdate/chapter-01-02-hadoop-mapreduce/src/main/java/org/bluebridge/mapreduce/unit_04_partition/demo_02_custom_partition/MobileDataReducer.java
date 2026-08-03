package org.bluebridge.mapreduce.unit_04_partition.demo_02_custom_partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 使用自定义分区实现类进行分区的 MobileDataReducer 类
 *
 * @author lingwh
 * @date 2026/8/1 15:13
 */
public class MobileDataReducer extends Reducer<Text, MobileDataWritable, Text, MobileDataWritable> {

    /**
     * Reducer 阶段业务逻辑
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<MobileDataWritable> values, Reducer<Text, MobileDataWritable, Text, MobileDataWritable>.Context context) throws IOException, InterruptedException {
        // 上行流量
        int totalUplinkData = 0;
        // 下行流量
        int totalDownlinkData = 0;
        // 总流量
        int totalSumData = 0;

        // 遍历当前相同手机号的一组流量信息
        for (MobileDataWritable mobileData : values) {
            // 累加当前手机号的上行流量
            totalUplinkData += mobileData.getUplinkData();
            // 累加当前手机号的下行流量
            totalDownlinkData += mobileData.getDownlinkData();
            // 累加当前手机号的总流量
            totalSumData += mobileData.getSumData();
        }

        // 封装输出结果
        MobileDataWritable outValue = new MobileDataWritable(totalUplinkData, totalDownlinkData, totalSumData);
        context.write(key, outValue);
    }
}