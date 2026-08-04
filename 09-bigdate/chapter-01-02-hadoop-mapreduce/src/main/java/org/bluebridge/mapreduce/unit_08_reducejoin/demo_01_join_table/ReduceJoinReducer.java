package org.bluebridge.mapreduce.unit_08_reducejoin.demo_01_join_table;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ReduceJoinReducer：按产品编号分组，合并订单和产品数据
 *
 * 为什么这里在循环之外定义了一个 productName 变量，不怕在循环中反复赋值吗？
 *   shuffle 是按产 ey） 组 ，，一个 ReducerTask 中处理的所有数据的 key 是相同的，这里的 key 是产品编号，一个产品编号对应的产品名称只有一个，找到这个产品名称就可以了。
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
        for (OrderProductVOWritable orderProductVO : values) {
            System.out.println("Hadoop 对象复用验证 - orderProductVO 的 hash 值: " + System.identityHashCode(orderProductVO) + "，orderId: " + orderProductVO.getOrderId());
            if ("product".equals(orderProductVO.getSource())) {
                productName = orderProductVO.getProductName();
            } else if ("order".equals(orderProductVO.getSource())) {
                // 手动复制字段，避免 Hadoop 对象复用问题
                // 如果直接 orderList.add(value)，遍历结束后 List 中所有元素都指向同一个对象，内容全变成最后一条记录
                OrderProductVOWritable order = new OrderProductVOWritable();
                order.set("order", orderProductVO.getOrderId(), orderProductVO.getAmount(), "");
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
