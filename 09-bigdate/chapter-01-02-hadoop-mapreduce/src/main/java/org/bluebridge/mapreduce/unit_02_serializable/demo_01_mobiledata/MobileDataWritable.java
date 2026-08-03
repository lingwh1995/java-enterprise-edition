package org.bluebridge.mapreduce.unit_02_serializable.demo_01_mobiledata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 自定义序列化对象实现合并相同手机号数据的 MobileData 类
 *
 * @author lingwh
 * @date 2026/7/19 19:05
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MobileDataWritable implements Writable {

    /**
     * 上行流量
     */
    private Integer uplinkData;

    /**
     * 下行流量
     */
    private Integer downlinkData;

    /**
     * 上下行流量总和
     */
    private Integer sumData;

    public void setSumData() {
        sumData = uplinkData + downlinkData;
    }

    @Override
    public String toString() {
        return String.format("%-15d\t%-15d\t%-15d", uplinkData, downlinkData, sumData);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(uplinkData == null ? 0 : uplinkData);
        out.writeInt(downlinkData == null ? 0 : downlinkData);
        out.writeInt(sumData == null ? 0 : sumData);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        uplinkData = in.readInt();
        downlinkData = in.readInt();
        sumData = in.readInt();
    }
}