package org.bluebridge.mapreduce.unit_05_shuffle.demo_01_mobiledata_sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 移动流量类
 *
 * @author lingwh
 * @date 2026/7/19 19:05
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MobileData implements Writable {

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
        out.writeInt(uplinkData);
        out.writeInt(downlinkData);
        out.writeInt(sumData);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        uplinkData = in.readInt();
        downlinkData= in.readInt();
        sumData = in.readInt();
    }
}