package org.bluebridge.mapreduce.unit_09_mapjoin.demo_01_join_table;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * MapJoinDriver：Map Join 驱动类
 *
 * 1. 功能描述：合并订单表和产品表，以产品编号关联
 * 2. 注意事项：在本地 IDEA 中测试时，输出结果文件路径在 target/classes/hadoop/output/unit_09_mapjoin/demo_01_join_table 目录中，需要手动查看运行结果
 *
 * @author lingwh
 * @date 2026/8/4 22:03
 */
public class MapJoinDriver {

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
        Job job = Job.getInstance(conf, "MapJoin");

        // 3. 设置 Job 类的驱动类
        job.setJarByClass(MapJoinDriver.class);

        // 4. 指定 Mapper 和 Reducer 处理类
        job.setMapperClass(MapJoinMapper.class);

        // 5. 设置 Map 阶段输出键值对的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        // --------------------- 设置 ReduceTask 数量开始 ---------------------
        // 6. 设置 Reduce 阶段输出键值对的类型
        // 特别注意： Map Join 中，不需要 Reduce 阶段，所以这里不用设置输出 Reduce 阶段的键值对类型，而且要把 ReduceTask 的数量设置为0
        // 设置ReduceTask的数量为0
        job.setNumReduceTasks(0);
        // --------------------- 设置 ReduceTask 数量结束 ---------------------

        // --------------------- 设置分布式缓存文件的路径开始 ---------------------
        Path inputPath;
        Path outputPath;
        if (args.length >= 2) {
            //集群运行，使用HDFS路径设置分布式缓存文件的路径
            Path hdfsCacheFilePath = new Path("/input/unit_09_mapjoin/demo_01_cache_file/product.txt");
            job.addCacheFile(hdfsCacheFilePath.toUri());
        } else {
            // 使用相对路径设置分布式缓存文件的路径
            Path basePath = new Path(MapJoinDriver.class.getClassLoader().getResource("").toURI());
            Path cacheFilePath = new Path(basePath, "hadoop/input/unit_09_mapjoin/demo_01_cache_file/product.txt");
            File cacheFile = new File(cacheFilePath.toUri());
            /**
             * 关于分布式缓存的解释?
             * Hadoop 会把 addCacheFile 声明的文件自动分发到每个运行 MapTask 的节点本地，副本随节点分布——所以叫"分布式缓存"。addCacheFile 只是声明，MapTask 的分布式调度才是"分布式"的根源。
             */
            job.addCacheFile(cacheFile.toURI());

            // 使用绝对路径设置分布式缓存文件的路径
            // job.addCacheFile(URI.create("file:///D:/input/cachefile/pd.txt"));
        }
        // --------------------- 设置分布式缓存文件的路径结束 ---------------------

        // 7. 设置输入、输出路径，自动删除输出目录（避免已存在报错）
        if (args.length >= 2) {
            inputPath = new Path(args[0]);
            outputPath = new Path(args[1]);
        } else {
            Path basePath = new Path(MapJoinDriver.class.getClassLoader().getResource("").toURI());
            inputPath = new Path(basePath, "hadoop/input/unit_09_mapjoin/demo_01_join_table");
            outputPath = new Path(basePath, "hadoop/output/unit_09_mapjoin/demo_01_join_table");
        }
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