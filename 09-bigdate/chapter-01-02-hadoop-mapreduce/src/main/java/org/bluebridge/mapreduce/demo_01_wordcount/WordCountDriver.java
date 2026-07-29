package org.bluebridge.mapreduce.demo_01_wordcount;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Driver 类，用于提交 Job
 *
 * 注意：在本地 IDEA 中测试时，输出结果文件路径在 target/classes/hadoop/output/part-r-00000 中，需要手动查看内容
 *
 * @author lingwh
 * @date 2025/8/20 09:17
 */
@Slf4j
public class WordCountDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        log.info("执行链路 - 开始执行 WordCountDriver.main()......");

        // 1. 创建配置对象
        Configuration conf = new Configuration();

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

        // 7. 设置输入、输出路径
        // 默认从 args 获取（jar 包运行方式），未传参时使用 maven resources 路径（本地测试）
        Path inputPath;
        Path outputPath;

        if (args.length >= 2) {
            // jar 包运行方式：通过命令行参数指定输入、输出路径
            inputPath = new Path(args[0]);
            outputPath = new Path(args[1]);
        } else {
            // 本地测试方式：使用 maven resources 中的输入文件
            URL resource = WordCountDriver.class.getClassLoader().getResource("hadoop/input/demo_01_wordcount/");
            if (resource == null) {
                System.err.println("未找到 input.txt，请检查 resources/hadoop/input/demo_01_wordcount/ 路径！");
                return;
            }
            inputPath = new Path(resource.toURI());
            outputPath = new Path(inputPath.getParent().getParent(), "output");
        }

        // 8. 自动删除输出目录（避免已存在报错）
        FileSystem fs = FileSystem.get(conf);
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