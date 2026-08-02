package org.bluebridge.mapreduce.unit_05_shuttle.demo_04_comparator_2_partsort;

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
        super(MobileData.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        MobileData aBean = (MobileData) a;
        MobileData bBean = (MobileData) b;
        return bBean.getUplinkData().compareTo(aBean.getUplinkData());
    }
}
