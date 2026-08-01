package org.bluebridge.mapreduce.unit_05_shuttle.demo_03_comparator_2_fullsort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * shuttle 时使用新创建类继承 WritableComparator 方式对文件进行全排序的 MobileDataComparator 类
 *
 * 先根据总流量进行降序排序，总流量相同时再根据上行流量降序排序比较器
 *
 * @author lingwh
 * @date 2026/8/1 20:41
 */
public class SortBySumDataDescAndUplinkDataDescComparator extends WritableComparator {

    public SortBySumDataDescAndUplinkDataDescComparator() {
        super(MobileData.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        MobileData aBean = (MobileData) a;
        MobileData bBean = (MobileData) b;
        // 第一排序：总流量降序
        int result = bBean.getSumData().compareTo(aBean.getSumData());
        // 总流量相同时，第二排序：上行流量降序
        if (result == 0) {
            result = bBean.getUplinkData().compareTo(aBean.getUplinkData());
        }
        return result;
    }

}
