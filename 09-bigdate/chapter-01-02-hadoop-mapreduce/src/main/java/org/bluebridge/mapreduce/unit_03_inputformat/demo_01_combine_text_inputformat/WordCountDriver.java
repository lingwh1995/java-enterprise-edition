package org.bluebridge.mapreduce.unit_03_inputformat.demo_01_combine_text_inputformat;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 使用 CombineTextInputFormat 先合并多个小文件再进行处理的 WordCountDriver 类
 *
 * 1. 添加如下代码来解决小文件场景下 MapTask 数量过多问题
 *    CombineTextInputFormat.setMaxInputSplitSize(job, 4 * 1024 * 1024);
 *    conf.set("mapreduce.job.inputformat.class",
 *    CombineTextInputFormat.class.getName());
 * 2. 查看配置效果
 *    在日志中搜索 number of splits 可以查看到 number of splits:1，说明多个小文件被合并为一个大文件然后再产生了一个切片
 * 2. 注意事项
 *    在本地 IDEA 中测试时，输出结果文件路径在 target/classes/hadoop/output/part-r-00000 中，需要手动查看内容
 *
 * @author lingwh
 * @date 2026/7/20 09:17
 */
@Slf4j
public class WordCountDriver {

    public static void main(String[] args)
            throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        log.info("执行链路 - 开始执行 WordCountDriver.main()......");

        // 1. 创建配置对象
        Configuration conf = new Configuration();

        // 检查 HDFS 连接是否正常（如果配置了 fs.defaultFS 指向 HDFS）
        String defaultFS = conf.get("fs.defaultFS", "file:///");
        if (defaultFS.startsWith("hdfs://")) {
            try {
                FileSystem.get(conf).getStatus();
            } catch (IOException e) {
                System.err.println("无法连接 HDFS: " + defaultFS + "，请确认 Hadoop 已启动");
                System.exit(1);
            }
        }

        // --------------------- 设置 InputFormat 实现类开始 ---------------------
        // 设置 InputFormat 类为 CombineTextInputFormat 写法一
        // conf.set("mapreduce.job.inputformat.class",
        // CombineTextInputFormat.class.getName());
        // --------------------- 设置 InputFormat 实现类结束 ---------------------

        // 2. 创建 Job 对象
        Job job = Job.getInstance(conf, "word count");

        // 3. 设置 Job 类的驱动类
        job.setJarByClass(WordCountDriver.class);

        // 4. 设置 Map 阶段输出键值对的类型
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 5. 设置 Map 端输出 KV 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 6. 设置 Reduce 阶段输出键值对的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // --------------------- 指定小文件场景文件大小开始 ---------------------
        CombineTextInputFormat.setMaxInputSplitSize(job, 4 * 1024 * 1024);
        // 设置 InputFormat 类为 CombineTextInputFormat 写法二（推荐）
        job.setInputFormatClass(CombineTextInputFormat.class);
        // --------------------- 指定小文件场景文件大小结束 ---------------------

        // 7. 设置输入、输出路径
        // 默认从 args 获取（jar 包运行方式），未传参时使用 maven resources 路径（本地测试）
        Path inputPath;
        Path outputPath;

        if (args.length >= 2) {
            // jar 包运行方式：通过命令行参数指定输入、输出路径
            inputPath = new Path(args[0]);
            outputPath = new Path(args[1]);
        } else {
            Path basePath = new Path(WordCountDriver.class.getClassLoader().getResource("").toURI());
            inputPath = new Path(basePath, "hadoop/input/unit_03_inputformat/demo_01_combine_text_inputformat");
            outputPath = new Path(basePath, "hadoop/output/unit_03_inputformat/demo_01_combine_text_inputformat");
        }

        // 8. 自动删除输出目录（避免已存在报错）
        FileSystem fs = outputPath.getFileSystem(conf);
        if (fs.exists(outputPath)) {
            fs.delete(outputPath, true);
        }

        // 9. 绑定输入输出
        FileInputFormat.addInputPath(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);

        // 10. 提交任务并设置退出码
        boolean success = job.waitForCompletion(true);
        System.exit(success ? 0 : 1);

        log.info("执行链路 - 结束执行 WordCountDriver.main()......");
    }
}