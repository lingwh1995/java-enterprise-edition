package org.bluebridge.mapreduce.unit_08_reducejoin.demo_02_join_table_v2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * JoinMapper：读取订单和产品两个文件，以产品编号为 key 输出
 * 通过 FileSplit 判断当前读取的是哪个文件
 *
 * @author lingwh
 * @date 2026/8/3 23:16
 */
public class ReduceJoinMapper extends Mapper<LongWritable, Text, Text, OrderProductVOWritable> {

    private Text outk = new Text();
    private OrderProductVOWritable outv = new OrderProductVOWritable();

    private String fileName;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取切片对象
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        fileName = fileSplit.getPath().getName();
    }

    /**
     * Map端核心业务逻辑（将要关联的数据进行整合，封装输出的内容 对数据的来源做出区分）
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取当前数据
        String lineData = value.toString();
        // 切割
        String[] datas = lineData.split("\t");
        // 对当前处理的文件做出区分
        if (fileName.equals("order.txt")){
            // 当前数据来源自order文件   1001	01	1
            outk.set(datas[1]);
            outv.setOrderId(Integer.parseInt(datas[0]));
            outv.setPid(Integer.parseInt(datas[1]));
            outv.setAmount(Integer.parseInt(datas[2]));
            outv.setPname("");
            outv.setTitle(fileName);
        }else {
            // 当前数据来源自pd文件  01	小米
            outk.set(datas[0]);
            outv.setOrderId(0);
            outv.setPid(Integer.parseInt(datas[0]));
            outv.setAmount(0);
            outv.setPname(datas[1]);
            outv.setTitle(fileName);
        }

        context.write(outk, outv);
    }
}
