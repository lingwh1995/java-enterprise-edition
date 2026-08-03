package org.bluebridge.mapreduce.unit_02_serializable.demo_01_flow;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 自定义序列化对象实现合并相同手机号数据的 FlowDriver 类
 *
 * 注意：在本地 IDEA 中测试时，输出结果文件路径在 target/classes/hadoop/output/unit_02_serializable/demo_01_flow 目录中，需要手动查看运行结果
 *
 * @author lingwh
 * @date 2026/7/19 20:29
 */
public class FlowDriver {

    public static void main(String[] args)
            throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
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

        // 2. 创建 Job 对象
        Job job = Job.getInstance(conf, "flow count");

        // 3. 设置 Job 类的驱动类
        job.setJarByClass(FlowDriver.class);

        // 4. 设置 Map 阶段输出键值对的类型
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        // 5. 设置 Map 端输出 KV 类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowWritable.class);

        // 6. 设置 Reduce 阶段输出键值对的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowWritable.class);

        // 7. 设置输入、输出路径
        // 默认从 args 获取（jar 包运行方式），未传参时使用 maven resources 路径（本地测试）
        Path inputPath;
        Path outputPath;

        if (args.length >= 2) {
            // jar 包运行方式：通过命令行参数指定输入、输出路径
            inputPath = new Path(args[0]);
            outputPath = new Path(args[1]);
        } else {
            Path basePath = new Path(FlowDriver.class.getClassLoader().getResource("").toURI());
            inputPath = new Path(basePath, "hadoop/input/unit_02_serializable/demo_01_flow");
            outputPath = new Path(basePath, "hadoop/output/unit_02_serializable/demo_01_flow");
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
    }
}