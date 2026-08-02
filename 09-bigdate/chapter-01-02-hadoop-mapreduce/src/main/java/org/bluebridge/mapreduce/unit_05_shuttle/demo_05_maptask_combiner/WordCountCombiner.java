package org.bluebridge.mapreduce.unit_05_shuttle.demo_05_maptask_combiner;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 在 MapTask 阶段额外执行 combiner 操作的 WordCountCombiner 类
 *
 * 在当前 MapTask 阶段额外执行 combiner 操作，将相同键的值进行聚合处理，其工作流程和 Reducer 阶段相同
 * @author lingwh
 * @date 2026/8/2 11:33
 */
@Slf4j
public class WordCountCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

    /**
     * Reduce 任务是高频循环，大量短生命周期对象会加重 JVM 垃圾回收 (GC)，拖慢 MR 运行效率。
     * Reduceucer 初始化时只创建一次 IntWritable，整个 reduce 阶段循环复用。
     */
    private final IntWritable outVal = new IntWritable();

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
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        log.info("执行链路 - 开始执行 WordCountReducer.reduce()......");

        int sum = 0;
        // 遍历 values 集合，累加每个 IntWritable 对象的值
        for (IntWritable value : values) {
            log.info("执行链路 - 当前 key: {}, value: {}", key, value.get());
            sum += value.get();
        }
        // 设置输出值为累加和
        outVal.set(sum);
        // 输出键值对
        context.write(key, outVal);

        log.info("执行链路 - 结束执行 WordCountReducer.reduce()......");
    }

}
