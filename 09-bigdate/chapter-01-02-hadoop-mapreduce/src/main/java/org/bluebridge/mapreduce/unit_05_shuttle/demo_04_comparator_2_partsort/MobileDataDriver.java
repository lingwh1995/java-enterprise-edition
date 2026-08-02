package org.bluebridge.mapreduce.unit_05_shuttle.demo_04_comparator_2_partsort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.bluebridge.mapreduce.unit_05_shuttle.demo_03_comparator_2_fullsort.SortBySumDataDescAndUplinkDataDescComparator;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * shuttle 时使用新创建类继承 WritableComparator 方式对文件进行分区内排序的 MobileDataDriver 类
 *
 * 按总流量从大到小排序驱动类
 *
 * @author lingwh
 * @date 2026/8/1 23:05
 */
public class MobileDataDriver {

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
        Job job = Job.getInstance(conf, "mobile data sort");

        // 3. 设置 Job 类的驱动类
        job.setJarByClass(MobileDataDriver.class);

        // 4. 设置 Map 阶段输出键值对的类型
        job.setMapperClass(MobileDataMapper.class);
        job.setReducerClass(MobileDataReducer.class);
        // 使用 1 个 Reducer 确保全局有序
        job.setNumReduceTasks(1);

        // 5. 设置 Map 端输出 KV 类型
        job.setMapOutputKeyClass(MobileData.class);
        job.setMapOutputValueClass(NullWritable.class);

        // 6. 设置 Reduce 阶段输出键值对的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(MobileData.class);

        // --------------------- 设置自定义 Partition 开始 ---------------------
        job.setPartitionerClass(MobileDataPartitioner.class);
        job.setNumReduceTasks(4);
        // --------------------- 设置自定义 Partition 结束 ---------------------

        // --------------------- 设置自定义 排序比较器 开始 ---------------------
        // 根据总流量进行降序排序
        //job.setSortComparatorClass(SortBySumDataDescComparator.class);
        // 根据上行流量进行降序排序
        //job.setSortComparatorClass(SortByUplinkDataDescComparator.class);
        // 先根据总流量进行降序排序，总流量相同时再根据上行流量降序排序比较器
        job.setSortComparatorClass(SortBySumDataDescAndUplinkDataDescComparator.class);
        // --------------------- 设置自定义 排序比较器 结束 ---------------------

        // 7. 设置输入、输出路径
        Path inputPath;
        Path outputPath;

        if (args.length >= 2) {
            inputPath = new Path(args[0]);
            outputPath = new Path(args[1]);
        } else {
            Path basePath = new Path(MobileDataDriver.class.getClassLoader().getResource("").toURI());
            inputPath = new Path(basePath, "hadoop/input/unit_05_shuttle/demo_04_comparator_2_partsort");
            outputPath = new Path(basePath, "hadoop/output/unit_05_shuttle/demo_04_comparator_2_partsort");
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
