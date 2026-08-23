package org.bluebridge.mapreduce.unit_10_multiple_queues;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 统计单词出现次数的 Reducer 类
 *
 * 自定义 Reducer 类将输入的文本数据转换为键值对格式步骤
 * 1. 定义 Reducer 类， 用需要继承 Hadoop 提供的 Reducer 类
 * 2. 自定义 Reducer 类的泛型参数
 *    Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT> 四大泛型参数
 *    KEYIN：输入的键类型，这里为 Text，表示输入的键是单词
 *    VALUEIN：输入的值类型，这里为 IntWritable，表示输入的值是单词出现次数
 *    KEYOUT：输出的键类型，这里为 Text，表示输出的键是单词
 *    VALUEOUT：输出的值类型，这里为 IntWritable，表示输出的值是 1，表示单词出现一次
 * 3. 重写 Reducer 类的 reduce()，在此方法中实现 Reducer 阶段业务逻辑
 *
 * @author lingwh
 * @date 2026/7/18 16:17
 */
@Slf4j
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

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