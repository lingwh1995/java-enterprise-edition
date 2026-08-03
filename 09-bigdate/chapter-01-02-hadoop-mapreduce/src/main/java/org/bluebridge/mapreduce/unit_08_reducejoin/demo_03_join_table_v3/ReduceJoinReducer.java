package org.bluebridge.mapreduce.unit_08_reducejoin.demo_03_join_table_v3;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * ReduceJoinReducer：按产品编号分组，合并订单和产品数据
 *
 * @author lingwh
 * @date 2026/8/2 20:30
 */
public class ReduceJoinReducer extends Reducer<Text, OrderProductVOWritable, NullWritable, OrderProductVOWritable> {

    private OrderProductVOWritable outValue = new OrderProductVOWritable();

    @Override
    protected void reduce(Text key, Iterable<OrderProductVOWritable> values, Context context)
            throws IOException, InterruptedException {
        String productName = "";

        // 第一轮遍历：先找到产品名称
        for (OrderProductVOWritable value : values) {
            if ("product".equals(value.getSource())) {
                productName = value.getProductName();
                break;
            }
        }

        // 第二轮遍历：输出每条订单合并产品名称
        for (OrderProductVOWritable value : values) {
            if ("order".equals(value.getSource())) {
                outValue.set("order", value.getOrderId(), value.getAmount(), productName);
                context.write(NullWritable.get(), outValue);
            }
        }
    }
}
