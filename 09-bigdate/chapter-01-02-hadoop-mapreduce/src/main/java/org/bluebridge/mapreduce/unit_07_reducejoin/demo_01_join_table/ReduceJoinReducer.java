package org.bluebridge.mapreduce.unit_07_reducejoin.demo_01_join_table;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * JoinReducer：按产品编号分组，合并订单和产品数据
 * 同一个产品编号下可能有多条订单和一条产品信息
 *
 * @author lingwh
 * @date 2026/8/2 20:30
 */
public class ReduceJoinReducer extends Reducer<Text, OrderProductVO, NullWritable, OrderProductVO> {

    private OrderProductVO outValue = new OrderProductVO();

    @Override
    protected void reduce(Text key, Iterable<OrderProductVO> values, Context context)
            throws IOException, InterruptedException {
        String productName = "";

        // 第一轮遍历：先找到产品名称
        for (OrderProductVO bean : values) {
            if ("product".equals(bean.getSource())) {
                productName = bean.getProductName();
                break;
            }
        }

        // 第二轮遍历：输出每条订单合并产品名称
        for (OrderProductVO bean : values) {
            if ("order".equals(bean.getSource())) {
                outValue.set("order", bean.getOrderId(), bean.getAmount(), productName);
                context.write(NullWritable.get(), outValue);
            }
        }
    }
}
