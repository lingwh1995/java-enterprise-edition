package org.bluebridge.mapreduce.unit_08_reducejoin.demo_01_join_table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * OrderProductVO：ReduceJoin 的数据载体
 *
 * 用于在 Mapper 和 Reducer 之间传递订单和产品数据
 *
 * @author lingwh
 * @date 2026/8/2 20:30
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProductVOWritable implements Writable {

    /**
     * 数据来源标记：order 表示来自订单表，product 表示来自产品表
     */
    private String source;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 订单数量
     */
    private Integer amount;

    /**
     * 产品名称
     */
    private String productName;

    public void set(String source, String orderId, Integer amount, String productName) {
        this.source = source;
        this.orderId = orderId;
        this.amount = amount;
        this.productName = productName;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(source == null ? "" : source);
        out.writeUTF(orderId == null ? "" : orderId);
        out.writeInt(amount == null ? 0 : amount);
        out.writeUTF(productName == null ? "" : productName);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        source = in.readUTF();
        orderId = in.readUTF();
        amount = in.readInt();
        productName = in.readUTF();
    }

    @Override
    public String toString() {
        return orderId + "\t" + productName + "\t" + amount;
    }
}
