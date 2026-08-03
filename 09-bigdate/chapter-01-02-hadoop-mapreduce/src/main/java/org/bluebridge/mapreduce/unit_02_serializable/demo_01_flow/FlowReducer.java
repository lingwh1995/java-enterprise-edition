package org.bluebridge.mapreduce.unit_02_serializable.demo_01_flow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 自定义序列化对象实现合并相同手机号数据的 FlowReducer 类
 *
 * @author lingwh
 * @date 2026/7/19 18:06
 */
public class FlowReducer extends Reducer<Text, FlowWritable, Text, FlowWritable> {

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
    protected void reduce(Text key, Iterable<FlowWritable> values, Reducer<Text, FlowWritable, Text, FlowWritable>.Context context) throws IOException, InterruptedException {
        // 上行流量
        int totalUplinkFlow = 0;
        // 下行流量
        int totalDownlinkFlow = 0;
        // 总流量
        int totalSumFlow = 0;

        // 遍历当前相同手机号的一组流量信息
        for (FlowWritable flowWritable : values) {
            // 累加当前手机号的上行流量
            totalUplinkFlow += flowWritable.getUplinkData();
            // 累加当前手机号的下行流量
            totalDownlinkFlow += flowWritable.getDownlinkData();
            // 累加当前手机号的总流量
            totalSumFlow += flowWritable.getSumData();
        }

        // 封装输出结果
        FlowWritable outValue = new FlowWritable(totalUplinkFlow, totalDownlinkFlow, totalSumFlow);
        context.write(key, outValue);
    }
}