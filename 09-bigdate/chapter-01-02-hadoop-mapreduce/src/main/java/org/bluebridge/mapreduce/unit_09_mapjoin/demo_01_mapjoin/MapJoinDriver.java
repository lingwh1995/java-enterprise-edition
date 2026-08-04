package org.bluebridge.mapreduce.unit_09_mapjoin.demo_01_mapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;

public class MapJoinDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // 创建配置对象
        Configuration conf = new Configuration();
        // 创建Job
        Job job = Job.getInstance(conf);
        // 指定当前Job的Mapper
        job.setMapperClass(MapJoinMapper.class);
        // 指定Map阶段输出的key和value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        // 指定最终输出结果的key和value的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        // 设置ReduceTask的数量为0
        job.setNumReduceTasks(0);
        // 设置分布式缓存文件的路径
        job.addCacheFile(URI.create("file:///E:/input/cachefile/pd.txt"));
        // 指定当前Job的输入和输出的路径
        FileInputFormat.addInputPath(job, new Path("E:\\input\\mapjoin"));
        FileOutputFormat.setOutputPath(job, new Path("E:\\output\\mapjoin_out2"));
        // 提交Job
        job.waitForCompletion(true);
    }
}
