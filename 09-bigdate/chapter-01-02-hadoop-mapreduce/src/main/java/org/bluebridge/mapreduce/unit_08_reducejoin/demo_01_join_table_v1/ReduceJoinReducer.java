package org.bluebridge.mapreduce.unit_08_reducejoin.demo_01_join_table_v1;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * ReduceJoinReducer：按产品编号分组，合并订单和产品数据
 *
 * v1.0 版本说明
 * 1. 程序写的很不好
 *    - BeanUtils.copyProperties 不必要 ：Hadoop 的 Iterable 中对象是复用的，直接复制字段即可，不需要引入 BeanUtils 做反射拷贝，性能差
 *    - ArrayList 缓存有内存风险 ：大数据量下同一产品编号的订单过多会撑爆内存
 * 2. 程序非常有意义，很有助于理解 Shuffle 内部处理时的一些机制
 *    每次循环只能把一个产品名称找出来，这样之所以是正确的是因为 Shuffle 是按产品编号（key）分组 的，每个产品编号对应的产品名称只有一个，找到这个产品名称就可以了。
 *
 * @author lingwh
 * @date 2026/8/3 23:17
 */
public class ReduceJoinReducer extends Reducer<Text, OrderProductVOWritable, OrderProductVOWritable, NullWritable> {

    private ArrayList<OrderProductVOWritable> orderList = new ArrayList<>();
    private OrderProductVOWritable productInfo = new OrderProductVOWritable();

    /**
     * Reduce阶段核心业务（针对相同 key 的一组数据进行关联操作，首先根据数据来源的不同将数据分离，然后再关联）
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<OrderProductVOWritable> values, Context context)
            throws IOException, InterruptedException {
        // 遍历当前相同 pid 的一组数据，根据不同数据来源进行分离
        for (OrderProductVOWritable orderProductVO : values) {
            if (orderProductVO.getTitle().equals("order.txt")) {
                // 数据来源是 order 文件
                try {
                    OrderProductVOWritable order = new OrderProductVOWritable();
                    BeanUtils.copyProperties(order, orderProductVO);
                    orderList.add(order);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 数据来源是 product 文件
                try {
                    BeanUtils.copyProperties(productInfo, orderProductVO);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // 遍历订单列表，给其中 pname 属性赋值
        for (OrderProductVOWritable order : orderList) {
            order.setPname(productInfo.getPname());
            context.write(order, NullWritable.get());
        }

        // 清空集合
        orderList.clear();
    }
}
