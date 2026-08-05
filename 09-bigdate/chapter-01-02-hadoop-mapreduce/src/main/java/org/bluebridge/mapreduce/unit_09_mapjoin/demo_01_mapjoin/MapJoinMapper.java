package org.bluebridge.mapreduce.unit_09_mapjoin.demo_01_mapjoin;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;

/**
 * MapJoinMapper：读取订单和产品两个文件，以产品编号为 key 输出
 *
 * 通过 FileSplit 判断当前读取的是哪个文件
 *
 * @author lingwh
 * @date 2026/8/4 22:36
 */
public class MapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private HashMap<String, String> productMap = new HashMap<>();
    private Text outk = new Text();

    // 在 MapTask 执行开始之前执行一次，将产品表的数据读取到内存中
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取分布式缓存文件的路径
        URI[] cacheFiles = context.getCacheFiles();
        URI cacheFile = cacheFiles[0];
        // 获取文件系统对象
        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
        // 获取输入流对象
        FSDataInputStream inputStream = fileSystem.open(new Path(cacheFile));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

        String line;
        while (StringUtils.isNotEmpty(line = bufferedReader.readLine())){
            // 切割  01	小米
            String[] datas = line.split("\t");
            productMap.put(datas[0], datas[1]);
        }

        // 关闭资源
        IOUtils.closeStream(fileSystem);
        IOUtils.closeStream(bufferedReader);
    }

    /**
     * Map端的核心业务（读取大表数据，针对每一行进行切割，根据pid到内存中获取对应的pname）
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取当前行数据
        String lineData = value.toString();
        // 切割   1001	01	1
        String[] datas = lineData.split("\t");
        // 获取pid
        String productId = datas[1];
        // 根据pid到内存中的容器中获取pname
        String productName = productMap.get(productId);
        // 封装输出结果
        String result = datas[0] + "\t" + productName + "\t" + datas[2];
        outk.set(result);
        context.write(outk, NullWritable.get());
    }
}
