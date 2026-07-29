package org.bluebridge.mapreduce.demo_01_wordcount;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 自定义 Mapper 类将输入的文本数据转换为键值对格式步骤
 *
 * 1. 自定义 Mapper 类， 用需要继承 Hadoop 提供的 Mapper 类
 * 2. 自定义 Mapper 类的泛型参数
 *    Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT> 四大泛型参数
 *    KEYIN：输入的键类型，这里为 LongWritable，表示输入的键是行偏移量
 *    VALUEIN：输入的值类型，这里为 Text，表示输入的值是文本行
 *    KEYOUT：输出的键类型，这里为 Text，表示输出的键是单词
 *    VALUEOUT：输出的值类型，这里为 IntWritable，表示输出的值是 1，表示单词出现一次
 * 3. 重写 Mapper 类的 map()，在此方法中实现 Mapper 阶段业务逻辑
 *
 *
 * @author lingwh
 * @date 2025/8/20 10:33
 */
@Slf4j
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    /**
     * Map 任务是高频循环，大量短生命周期对象会加重 JVM 垃圾回收 (GC)，拖慢 MR 运行效率。
     * Mapper 初始化时只创建一次 Text、IntWritable，整个 map 阶段循环复用。
     */
    private final Text outKey = new Text();
    private final IntWritable outVal = new IntWritable(1);

    /**
     * Map 阶段核心业务逻辑
     *
     * @param key
     * @param value
     * @param context 上下文对象，在 mr 流程中起到了一个连接各个环节的作用
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        log.info("执行链路 - 开始执行 WordCountMapper.map()......");

        // 1. 获取当前行数据
        String line = value.toString();
        log.info("执行链路 - 当前行数据: {}", line);
        // 2. 按空格分隔单词
        String[] words = line.split(" ");
        // 更强的正则表达式，匹配多个空格或制表符等，可以防止空字符串进入数组
        //String[] words = line.split("\\s+");
        // 3. 遍历单词数组
        for(String word : words){
            // 4. 输出键值对
            // 复用对象，仅修改内部数据，不新建实例
            outKey.set(word);
            context.write(outKey, outVal);
        }

        log.info("执行链路 - 结束执行 WordCountMapper.map()......");
    }
}
