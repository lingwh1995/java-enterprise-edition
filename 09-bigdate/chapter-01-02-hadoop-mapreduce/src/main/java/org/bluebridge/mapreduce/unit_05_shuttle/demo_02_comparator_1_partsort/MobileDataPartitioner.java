package org.bluebridge.mapreduce.unit_05_shuttle.demo_02_comparator_1_partsort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * shuttle时使用实体类实现接口 WritableComparable 方式对文件进行分区内排序的 MobileDataPartitioner 类
 *
 * 自定义分区实现类，根据手机号前两位进行分区
 *
 * @author lingwh
 * @date 2026/8/1 19:49
 */
public class MobileDataPartitioner extends Partitioner<MobileData, NullWritable> {


    @Override
    public int getPartition(MobileData mobileData, NullWritable nullWritable, int numPartitions) {
        String phoneNumber = mobileData.getPhoneNumber();
        int partitions;
        if(phoneNumber.startsWith("13")) {
            partitions = 0;
        } else if(phoneNumber.startsWith("15")) {
            partitions = 1;
        } else if(phoneNumber.startsWith("18")) {
            partitions = 2;
        } else {
            partitions = 3;
        }
        return partitions;
    }
}