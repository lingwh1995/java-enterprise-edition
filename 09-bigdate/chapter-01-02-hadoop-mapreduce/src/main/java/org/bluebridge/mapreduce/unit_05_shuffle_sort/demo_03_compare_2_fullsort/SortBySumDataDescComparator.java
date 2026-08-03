package org.bluebridge.mapreduce.unit_05_shuffle_sort.demo_03_compare_2_fullsort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * shuttle 时使用新创建类继承 WritableComparator 方式对文件进行全排序的 FlowComparator 类
 *
 * 根据总流量进行降序排序比较器
 *
 * @author lingwh
 * @date 2026/8/1 20:59
 */
public class SortBySumDataDescComparator extends WritableComparator {

    public SortBySumDataDescComparator() {
        super(FlowWritable.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        FlowWritable aBean = (FlowWritable) a;
        FlowWritable bBean = (FlowWritable) b;
        return bBean.getSumData().compareTo(aBean.getSumData());
    }
}
