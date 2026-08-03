package org.bluebridge.mapreduce.unit_05_shuffle_sort.demo_02_compare_1_partsort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * shuttle 时使用实体类实现接口 WritableComparable 方式对文件进行分区内排序的 MobileData 类
 *
 * 移动流量类（实现 WritableComparable，按总流量降序排序）
 *
 * @author lingwh
 * @date 2026/8/1 19:02
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MobileDataWritable implements WritableComparable<MobileDataWritable> {

    /**
     * 手机号
     */
    private String phoneNumber;

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
        out.writeUTF(phoneNumber == null ? "" : phoneNumber);
        out.writeInt(uplinkData == null ? 0 : uplinkData);
        out.writeInt(downlinkData == null ? 0 : downlinkData);
        out.writeInt(sumData == null ? 0 : sumData);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        phoneNumber = in.readUTF();
        uplinkData = in.readInt();
        downlinkData = in.readInt();
        sumData = in.readInt();
    }

    @Override
    public int compareTo(MobileDataWritable o) {
        // 按总流量降序排序
        return o.getSumData().compareTo(this.getSumData());
    }
}
