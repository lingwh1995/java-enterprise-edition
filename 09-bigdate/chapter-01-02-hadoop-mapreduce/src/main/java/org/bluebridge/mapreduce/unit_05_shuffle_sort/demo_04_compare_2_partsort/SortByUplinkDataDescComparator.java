package org.bluebridge.mapreduce.unit_05_shuffle_sort.demo_04_compare_2_partsort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * shuttle 时使用新创建类继承 WritableComparator 方式对文件进行分区内排序的 SortByUplinkDataDescComparator 类
 *
 * 根据上行流量进行降序排序比较器
 *
 * @author lingwh
 * @date 2026/8/1 22:38
 */
public class SortByUplinkDataDescComparator extends WritableComparator {

    public SortByUplinkDataDescComparator() {
        super(FlowWritable.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        FlowWritable aBean = (FlowWritable) a;
        FlowWritable bBean = (FlowWritable) b;
        return bBean.getUplinkData().compareTo(aBean.getUplinkData());
    }
}
