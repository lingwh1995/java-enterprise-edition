package org.bluebridge.mapreduce.unit_08_reducejoin.demo_02_join_table_v2;

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
 * @date 2026/8/3 23:14
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProductVOWritable implements Writable {

    private Integer orderId;
    private Integer pid;
    private Integer amount;
    private String pname;
    private String title;

    @Override
    public String toString() {
        return orderId + "\t" + pname + "\t" + amount;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(orderId);
        out.writeInt(pid);
        out.writeInt(amount);
        out.writeUTF(pname);
        out.writeUTF(title);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        orderId = in.readInt();
        pid = in.readInt();
        amount = in.readInt();
        pname = in.readUTF();
        title = in.readUTF();
    }
}
