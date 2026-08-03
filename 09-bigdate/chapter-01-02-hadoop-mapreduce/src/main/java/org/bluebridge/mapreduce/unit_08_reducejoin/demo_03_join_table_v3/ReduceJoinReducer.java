package org.bluebridge.mapreduce.unit_08_reducejoin.demo_03_join_table_v3;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ReduceJoinReducer：按产品编号分组，合并订单和产品数据
 * 单轮遍历：缓存订单数据，同时找产品名称，最后统一输出
 *
 * @author lingwh
 * @date 2026/8/2 20:30
 */
public class ReduceJoinReducer extends Reducer<Text, OrderProductVOWritable, NullWritable, OrderProductVOWritable> {

    @Override
    protected void reduce(Text key, Iterable<OrderProductVOWritable> values, Context context)
            throws IOException, InterruptedException {
        String productName = "";
        List<OrderProductVOWritable> orderList = new ArrayList<>();

        // 单轮遍历：缓存订单数据，同时找产品名称
        for (OrderProductVOWritable value : values) {
            if ("product".equals(value.getSource())) {
                productName = value.getProductName();
            } else if ("order".equals(value.getSource())) {
                // 手动复制字段，避免 Hadoop 对象复用问题
                OrderProductVOWritable order = new OrderProductVOWritable();
                order.set("order", value.getOrderId(), value.getAmount(), "");
                orderList.add(order);
            }
        }

        // 遍历缓存的订单，赋值产品名称并输出
        for (OrderProductVOWritable order : orderList) {
            order.setProductName(productName);
            context.write(NullWritable.get(), order);
        }
    }
}
