package org.bluebridge.mapreduce.unit_04_partition.demo_02_custom_partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 *
 * @author lingwh
 * @date 2026/7/30 22:31
 */
public class MobileDataPartitioner extends Partitioner<Text, MobileData> {
    @Override
    public int getPartition(Text text, MobileData mobileData, int numPartitions) {
        String phoneNumber = text.toString();
        int partitions;
        if(phoneNumber.startsWith("135")) {
            partitions = 0;
        } else if(phoneNumber.startsWith("136")) {
            partitions = 1;
        } else if(phoneNumber.startsWith("137")) {
            partitions = 2;
        } else if(phoneNumber.startsWith("138")) {
            partitions = 3;
        }else {
            partitions = 4;
        }
        return 0;
    }
}
