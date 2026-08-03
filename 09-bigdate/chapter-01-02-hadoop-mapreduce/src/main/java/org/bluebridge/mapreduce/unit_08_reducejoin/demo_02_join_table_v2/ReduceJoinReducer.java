package org.bluebridge.mapreduce.unit_08_reducejoin.demo_02_join_table_v2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * ReduceJoinReducer：按产品编号分组，合并订单和产品数据
 *
 * @author lingwh
 * @date 2026/8/3 23:17
 */
public class ReduceJoinReducer extends Reducer<Text, OrderProductVOWritable, OrderProductVOWritable, NullWritable> {

    private OrderProductVOWritable outValue = new OrderProductVOWritable();

    @Override
    protected void reduce(Text key, Iterable<OrderProductVOWritable> values, Context context)
            throws IOException, InterruptedException {
        String productName = "";

        // 第一轮遍历：先找到产品名称
        for (OrderProductVOWritable orderProductVO : values) {
            if ("product.txt".equals(orderProductVO.getTitle())) {
                productName = orderProductVO.getPname();
                break;
            }
        }

        // 第二轮遍历：输出每条订单合并产品名称
        for (OrderProductVOWritable orderProductVO : values) {
            if ("order.txt".equals(orderProductVO.getTitle())) {
                outValue.setOrderId(orderProductVO.getOrderId());
                outValue.setPid(orderProductVO.getPid());
                outValue.setAmount(orderProductVO.getAmount());
                outValue.setPname(productName);
                outValue.setTitle(orderProductVO.getTitle());
                context.write(outValue, NullWritable.get());
            }
        }
    }
}
