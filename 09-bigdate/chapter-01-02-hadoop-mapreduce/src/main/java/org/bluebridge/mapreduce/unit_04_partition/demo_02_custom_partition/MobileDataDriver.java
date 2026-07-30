package org.bluebridge.mapreduce.unit_04_partition.demo_02_custom_partition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author lingwh
 * @date 2025/8/20 14:51
 */
public class MobileDataDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        // 1. 创建配置对象
        Configuration conf = new Configuration();

        // 2. 创建 Job 对象
        Job job = Job.getInstance(conf, "mobile data count");

        // 3. 设置 Job 类的驱动类
        job.setJarByClass(MobileDataDriver.class);

        // 4. 设置 Map 阶段输出键值对的类型
        job.setMapperClass(MobileDataMapper.class);
        job.setReducerClass(MobileDataReducer.class);

        // 5. 设置 Map 端输出 KV 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(MobileData.class);

        // 6. 设置 Reduce 阶段输出键值对的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(MobileData.class);

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
            URL resource = MobileDataDriver.class.getClassLoader().getResource("hadoop/input/unit_04_partition/demo_02_custom_partition");
            if (resource == null) {
                System.err.println("未找到 input.txt，请检查 resources/hadoop/input/unit_04_partition/demo_02_custom_partition 路径！");
                return;
            }
            inputPath = new Path(resource.toURI());
            outputPath = new Path(inputPath.getParent().getParent(), "output/unit_04_partition/demo_02_custom_partition");
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
    }
}