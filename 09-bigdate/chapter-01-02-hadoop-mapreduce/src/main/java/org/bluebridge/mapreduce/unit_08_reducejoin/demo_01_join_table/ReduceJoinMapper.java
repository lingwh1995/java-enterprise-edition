package org.bluebridge.mapreduce.unit_08_reducejoin.demo_01_join_table;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * JoinMapper：读取订单和产品两个文件，以产品编号为 key 输出
 * 
 * 通过 FileSplit 判断当前读取的是哪个文件
 *
 * @author lingwh
 * @date 2026/8/2 20:30
 */
public class ReduceJoinMapper extends Mapper<LongWritable, Text, Text, OrderProductVOWritable> {

    private Text outKey = new Text();
    private OrderProductVOWritable outValue = new OrderProductVOWritable();
    private String fileName;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取当前处理的文件名
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        fileName = fileSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] fields = line.split("\t");

        if ("order.txt".equals(fileName)) {
            // 订单表：订单编号 \t 产品编号 \t 订单数量
            String orderId = fields[0];
            String productId = fields[1];
            int amount = Integer.parseInt(fields[2]);
            outKey.set(productId);
            outValue.set("order", orderId, amount, "");
        } else if ("product.txt".equals(fileName)) {
            // 产品表：产品编号 \t 产品名称
            String productId = fields[0];
            String productName = fields[1].trim();
            outKey.set(productId);
            outValue.set("product", "", 0, productName);
        }

        context.write(outKey, outValue);
    }
}
