package org.bluebridge.mapreduce.unit_09_mapjoin.demo_01_join_table;

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
import java.util.Map;

/**
 * MapJoinMapper：读取订单和产品两个文件，以产品编号为 key 输出
 *
 * 通过 FileSplit 判断当前读取的是哪个文件
 *
 * @author lingwh
 * @date 2026/8/4 22:36
 */
public class MapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private Map<String, String> productMap = new HashMap<>();
    private Text outk = new Text();

    /**
     * 在 MapTask 执行开始之前执行一次，将产品表的数据读取到内存中
     *
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取分布式缓存文件的路径
        URI[] cacheFiles = context.getCacheFiles();
        URI cacheFile = cacheFiles[0];
        /**
         * 1. FileSystem.get() 的作用?
         *    根据缓存文件自身的 URI scheme 获取对应的文件系统对象（本地文件用 LocalFileSystem，HDFS 文件用 DistributedFileSystem）
         * 2. FileSystem.get() 两个重载方法的区别?
         *    - FileSystem.get(cacheFile, conf)：用缓存文件自身 URI 的 scheme 选文件系统，不管集群默认 fs，适配 hdfs/file，读分布式缓存推荐用，不会报 Wrong FS。
         *    - FileSystem.get(conf)：读取配置里fs.defaultFS拿默认文件系统，如果缓存文件不在默认 fs，就会报错。
         */
        FileSystem fileSystem = FileSystem.get(cacheFile, context.getConfiguration());
        // 获取输入流对象
        FSDataInputStream inputStream = fileSystem.open(new Path(cacheFile));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

        String line;
        while (StringUtils.isNotEmpty(line = bufferedReader.readLine())){
            // 去除行首 BOM 字符（UTF-8 文件第一行可能带 \uFEFF），并 trim 掉末尾多余空白
            line = line.replace("\uFEFF", "").trim();
            // 切割  01   小米
            String[] datas = line.split("\t");
            productMap.put(datas[0], datas[1]);
        }

        // 关闭资源：只关闭流，不要关闭FileSystem！
        IOUtils.closeStream(inputStream);
        IOUtils.closeStream(bufferedReader);
        // IOUtils.closeStream(fileSystem); // 删掉/注释掉这一行！！
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