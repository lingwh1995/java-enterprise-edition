package org.bluebridge.mapreduce.unit_04_partition.demo_02_custom_partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 使用自定义分区实现类进行分区的 MobileDataPartitioner 类
 *
 * 自定义分区实现类，根据手机号前两位进行分区
 *
 * @author lingwh
 * @date 2026/8/1 15:23
 */
public class MobileDataPartitioner extends Partitioner<Text, MobileData> {

    @Override
    public int getPartition(Text text, MobileData mobileData, int numPartitions) {
        String phoneNumber = text.toString();
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