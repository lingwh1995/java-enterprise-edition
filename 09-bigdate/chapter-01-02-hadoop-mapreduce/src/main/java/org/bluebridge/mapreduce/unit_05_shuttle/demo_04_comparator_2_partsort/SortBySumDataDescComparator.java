package org.bluebridge.mapreduce.unit_05_shuttle.demo_04_comparator_2_partsort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * shuttle 时使用新创建类继承 WritableComparator 方式对文件进行分区内排序的 MobileDataComparator 类
 *
 * 根据总流量进行降序排序比较器
 *
 * @author lingwh
 * @date 2026/8/1 22:27
 */
public class SortBySumDataDescComparator extends WritableComparator {

    public SortBySumDataDescComparator() {
        super(MobileData.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        MobileData aBean = (MobileData) a;
        MobileData bBean = (MobileData) b;
        return bBean.getSumData().compareTo(aBean.getSumData());
    }
}
